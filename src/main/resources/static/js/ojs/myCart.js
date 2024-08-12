document.addEventListener('DOMContentLoaded', function() {
    updateTotalPrice();
});
 function changeQuantity(index, change) {
    console.log("index : "+index +", change : "+change);
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