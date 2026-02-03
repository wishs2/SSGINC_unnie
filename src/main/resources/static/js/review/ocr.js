// 최상단에 선언하여 모든 함수에서 접근 가능하게 함
let currentDraft = {};

/**
 * 1) OCR 업로드 및 결과 파싱
 */
document.getElementById("ocrForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const fileInput = document.getElementById("ocrFile");
    if (!fileInput.files.length) return alert("파일을 선택해주세요!");

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    try {
        const response = await fetch("/api/ocr/upload", {
            method: "POST",
            credentials: "include",
            body: formData
        });

        const result = await response.json();
        if (!result.data || !result.data.confirmed) {
            alert("OCR 분석 결과가 없습니다.");
            return;
        }

        // 1. 데이터 추출 및 currentDraft 할당
        // OCR 업로드 성공 후 처리 로직 부분
        const confirmedData = result.data.confirmed; // [ {type: 'SHOP_NAME', value: '...'}, ... ]

        let extracted = {};

        confirmedData.forEach(field => {
            console.log("OCR field raw:", field);

            const type = field.type;
            // value가 객체일 수도 있으므로 안전하게 처리
            const value =
                typeof field.value === "object" && field.value !== null
                    ? field.value.text ?? ""
                    : field.value;

            console.log("parsed type:", type, "parsed value:", value);

            switch (type) {
                case 'SHOP_NAME':
                    extracted.shopName = value;
                    document.getElementById("shopName").value = value;
                    break;

                case 'AMOUNT':
                    extracted.amount = value;
                    document.getElementById("totalAmount").value = value;
                    break;

                case 'DATE_TIME':
                    extracted.dateTime = value;
                    document.getElementById("paymentDate").value = value;
                    break;

                case 'BUSINESS_NUMBER':
                    extracted.businessNumber = value;
                    document.getElementById("businessNumber").value = value;
                    break;

                case 'APPROVAL_NUMBER':
                    extracted.approvalNumber = value;
                    document.getElementById("approvalNumber").value = value;
                    break;

                default:
                    console.warn("처리되지 않은 OCR 타입:", type);
            }
        });

        currentDraft = extracted; //전역 변수 업데이트
        console.log("최종 currentDraft:", currentDraft);

        // 2. UI 필드에 값 세팅 (사용자가 수정할 수 있도록)
        document.getElementById("shopName").value = currentDraft.shopName || "";
        document.getElementById("totalAmount").value = currentDraft.amount || "";
        document.getElementById("paymentDate").value = currentDraft.dateTime || "";

        // Hidden 필드에 저장 (수정 불가 데이터)
        document.getElementById("businessNumber").value = currentDraft.businessNumber || "";
        document.getElementById("approvalNumber").value = currentDraft.approvalNumber || "";

        document.getElementById("checkOcrSection").style.display = "block";

        if (!currentDraft.amount || !currentDraft.dateTime || !currentDraft.shopName
                || !currentDraft.businessNumber || !currentDraft.approvalNumber) {
                    document.getElementById("saveReceipt").disabled = true;
                }

    } catch (error) {
        console.error("OCR 실패:", error);
    }
});

/**
 * 2) 영수증 저장 (DTO 전송)
 */
document.getElementById("saveReceipt").addEventListener("click", async () => {

    // 1. DTO 구조에 맞게 데이터 수집
    const receiptRequest = {
        receiptShopName: document.getElementById("shopName").value,
        receiptAmount: parseInt(document.getElementById("totalAmount").value.replace(/[^0-9]/g, "") || 0),
        receiptDate: document.getElementById("paymentDate").value,
        receiptBusinessNumber: document.getElementById("businessNumber").value,
        receiptApprovalNumber: document.getElementById("approvalNumber").value
    };

    // 2. 검증 (바뀐 필드명으로 체크)
    if (!receiptRequest.receiptBusinessNumber || !receiptRequest.receiptShopName) {
        alert("영수증 정보(사업자번호 또는 업체명)가 부족합니다. 다시 시도해주세요.");
        return;
    }

    if (!receiptRequest.receiptAmount || !receiptRequest.receiptDate) {
        alert("결제 금액과 결제 날짜를 확인해주세요. 다시 시도해주세요.");
        return;
    }

    try {
        const response = await fetch("/api/receipt/save", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(receiptRequest)
        });

        const saveResult = await response.json();
        // 실패 케이스
        //영수증 중복 예외처리
        if (!response.ok) {
            if (saveResult.code === 'RV026') {
                alert('이미 리뷰가 작성된 영수증입니다.');
                return;
            }

            alert(saveResult.message || '영수증 저장에 실패했습니다.');
            return;
        }

        // 성공 케이스
        alert("영수증 인증이 완료되었습니다.");
        window.location.href = `/review/create?receiptId=${saveResult.data.receiptId}`;

    } catch (error) {
        console.error("저장 실패:", error);
        alert("서버 통신 중 오류가 발생했습니다.");
    }

});