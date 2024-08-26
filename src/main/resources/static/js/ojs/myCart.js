document.addEventListener('DOMContentLoaded', function() {
    updateTotalPrice();
});
 function changeQuantity(index, change) {
    var quantityElement = document.getElementById('quantity-' + index);
    var currentQuantity = parseInt(quantityElement.textContent) || 0;
    var newQuantity = currentQuantity + change;
    if (newQuantity > 0) {
    quantityElement.textContent = newQuantity;
    updateTotalPrice();
    }
 }
  function updateTotalPrice() {
  var cartItems = document.querySelectorAll('tr.TableContentRow');
  var totalPrice = 0;
    if (cartItems.length > 0) {
    cartItems.forEach(function(row) {
    var quantity = parseInt(row.querySelector('.SubTd_quantity').textContent);
    var price = parseInt(row.querySelector('.SubTd_price').textContent);
    var foodTotal = quantity * price;
    row.querySelector('.FoodTotal').textContent = foodTotal;
    totalPrice += foodTotal;
    });
    var TotalPriceElement = document.getElementById("allTotalPrice");
    TotalPriceElement.textContent = "총 상품 가격 = " + totalPrice;
   }
  }
function deleteItem(index) {
     const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
     const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    fetch('/deleteCartItem', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(index)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 프론트엔드에서 아이템 삭제
            var row = document.getElementById("row-" + index);
            if (row) {
                row.remove();
                // 아이템 삭제 후 인덱스 재계산
                updateItemIndices();
                updateTotalPrice();

                // 장바구니가 비어 있는지 확인
                if (data.empty) {
                    var TotalPriceElement = document.getElementById("allTotalPrice");
                    var storeNameDiv = document.getElementById("StoreNameDiv");
                    storeNameDiv.style.display = "none";
                    TotalPriceElement.textContent = "";
                    document.getElementById("buyFoodDiv").style.display = "none";
                    document.getElementById("cart-container").innerHTML = `
                      <td colspan="5" class="text-center">
                             <div class="empty-cart-div">
                                 <img class="empty-img" src="/img/mycartimg/EmptyCartIcon.png">
                                 <span class="empty-span">장바구니가 텅 비었어요.</span>
                             </div>
                         </td>`;
                }
                var container = document.getElementById("notification");
                var notification = document.querySelector(".notification-bubble");
                if(data.cartItemsSize == 0){
                   notification.style.visibility = "hidden";
                 }
                notification.textContent = data.cartItemsSize;
                const Toast = Swal.mixin({
                    toast: true,
                    showConfirmButton: false,
                    timer: 700,
                    timerProgressBar: true,
                });
                Toast.fire({
                    icon: 'success',
                    title: '삭제 되었습니다.'
                });
            }
        } else {
            alert("아이템 삭제에 실패했습니다.");
        }
    })
    .catch(error => console.error('Error:', error));
}

function updateItemIndices() {
    var rows = document.querySelectorAll('.TableContentRow');
    rows.forEach((row, index) => {
        row.id = "row-" + index;
        var deleteButton = row.querySelector('.DeleteBtn');
        if (deleteButton) {
            deleteButton.setAttribute('onclick', 'deleteItem(' + index + ')');
        }
    });
}

function IsNotLogin() {
            Swal.fire({
                icon: 'info',
                title: '로그인 필요',
                text: '로그인 페이지로 이동합니다.',
                toast: true,
                position: 'center',
                timer: 1000,
                timerProgressBar: true,
                showConfirmButton: false,
                background: '#f8f9fa',
                iconColor: '#007bff',
                customClass: {
                    title: 'swal2-title',
                    container: 'swal2-container'
                },
                willClose: () => {
                    window.location.href = '/Login.html';
                }
            });
        }
function OnOrderClick() {
            IMP.init("imp71404515");
            const merchantUid = `payment-${crypto.randomUUID()}`;
             IMP.request_pay(
               {
                 pg: "kicc",
                 pay_method: "card",
                 merchant_uid: merchantUid, // 상점에서 생성한 고유 주문번호
                 name: "주문명:결제테스트",
                 amount: 1004,
                 buyer_email: "test@portone.io",
                 buyer_name: "구매자이름",
                 buyer_tel: "010-1234-5678", // 필수
                 buyer_addr: "서울특별시 강남구 삼성동",
                 m_redirect_url: "{모바일에서 결제 완료 후 리디렉션 될 URL}",
               },
            function (rsp) {
            if (rsp.success) {
                // 결제 성공
                alert('결제가 완료되었습니다.');
                console.log('결제 정보:', rsp);
                // 결제 완료 후 추가 처리
            } else {
                // 결제 실패
                alert(`결제에 실패하였습니다. 에러 코드: ${rsp.error_code}, 에러 메시지: ${rsp.error_msg}`);
                console.log('결제 실패 정보:', rsp);
                // 결제 실패 후 추가 처리
            }
        }
    );
}
