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
    const deliTip = document.getElementById("deliTip").value;
    const numericValue = deliTip.match(/\d+/g);
    const tip = numericValue ? parseInt(numericValue[0], 10) : 0;
    if(tip === 0){
    TotalPriceElement.textContent = "총 상품 가격 "+totalPrice+"원 - 배달비 무료 = "+totalPrice+"원";
    }else{
    TotalPriceElement.textContent = "총 상품 가격 "+totalPrice+"원 - 배달비 "+ deliTip+"원 = "+(totalPrice-deliTip)+"원";
    }
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
            var totalPriceText = document.getElementById("allTotalPrice").textContent;
            let numbersString = totalPriceText.split('=')[1].trim();
            let totalPrice = numbersString.replace(/[^0-9]/g, '');
            totalPrice = parseInt(totalPrice,10);
            var cartItems = document.querySelectorAll('tr.TableContentRow');
            var row = document.getElementById('row-0');
            var foodNameSpan = row.querySelector('td.FoodImgNInfo .NameNDescDiv .card-title');
            var foodName = foodNameSpan.textContent.trim();
            const address = document.getElementById("user-address-input").value;
            const Name = document.getElementById("user-name").textContent;
            const Email = document.getElementById("user-email").textContent;
            const Phone = document.getElementById("user-phone").textContent;
            const store = document.getElementById("store-name").textContent;
            if(cartItems.length>1){
                foodName+= " 외 "+(cartItems.length-1)+"개"
            }
             IMP.request_pay(
               {
                 pg: "kicc",
                 pay_method: "card",
                 merchant_uid: merchantUid, // 상점에서 생성한 고유 주문번호
                 name: foodName,
                 amount: totalPrice,
                 buyer_email: Email,
                 buyer_name: Name,
                 buyer_tel: Phone, // 필수
                 buyer_addr: address,
               },
            function (rsp) {
            if (rsp.success) {
                // 결제 성공
                const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
                fetch('/add-to-order-history', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        [csrfHeader]: csrfToken
                    },
                    body: JSON.stringify({
                        food: foodName,
                        store: store,
                        totalPrice:totalPrice,
                    })
                })
                .then(response => response.json())
                .then(data => {
                    console.log('Response data:', data);
                    Swal.fire({
                                    icon: 'success',
                                    title: '결제 성공',
                                    text: '주문 목록으로 이동합니다.',
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
                                        window.location.href = '/myPage';
                                    }
                                });
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            } else {
                // 결제 실패
                console.log(`결제에 실패하였습니다. 에러 코드: ${rsp.error_code}, 에러 메시지: ${rsp.error_msg}`);
                // 결제 실패 후 추가 처리
            }
        }
    );
}
