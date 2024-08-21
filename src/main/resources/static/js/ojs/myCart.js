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
                        <tr class="EmptyContentRow">
                            <td colspan="5" class="text-center">장바구니가 비었습니다.</td>
                        </tr>`;
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


