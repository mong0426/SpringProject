    document.addEventListener("DOMContentLoaded", function() {              // 음식메뉴 , 가게 정보 , 리뷰 스크립트
        const foodMenuBtn = document.getElementById("FoodMenuBtn");
        const storeInfoBtn = document.getElementById("StoreInfoBtn");
        const reviewBtn = document.getElementById("ReviewBtn");
        const hiddenContents = document.querySelectorAll('.HiddenContent');
        const menus = document.querySelectorAll('.FoodMenuDiv');
        const info = document.getElementById("StoreInfoDiv");
        const reviewSpan = document.getElementById("reviewSpan");
        const review = document.getElementById("ReviewDiv");
        function handleClick(section) {
        document.querySelectorAll('.TopMenuStyle').forEach(btn => btn.classList.remove('SelectMenu'));
        this.classList.add('SelectMenu');
        hiddenContents.forEach(hiddenContent => hiddenContent.style.display = 'none');
        switch (section) {
            case "FoodMenu":
             menus.forEach(menu => menu.style.display = 'flex');
             break;
            case "StoreInfo":
             info.style.display= 'block';
             break;
            case "Review":
            review.style.display= 'block';
             break;
            }

        }
        foodMenuBtn.addEventListener("click", function() {
            handleClick.call(this, "FoodMenu");
        });

        storeInfoBtn.addEventListener("click", function() {
            handleClick.call(this, "StoreInfo");
        });

        reviewBtn.addEventListener("click", function() {
            handleClick.call(this, "Review");
        });

        reviewSpan.addEventListener("click", function(){
            reviewBtn.click();
        })
    });

document.addEventListener('DOMContentLoaded', function() {      //모달 내용표시 스크립트
    const modal = document.getElementById('clickedFoodModal');
    const closeModalBtn = document.getElementById('closeModal');
    const modalTitle = document.getElementById("modalTitle");
    const modalBody = document.getElementById("modalBody");
    const orderQuantityElement = document.getElementById('OrderQuantity');
    const minusBtn = document.getElementById('minusBtn');
    const plusBtn = document.getElementById('plusBtn');
    const orderBtn = document.getElementById("OrderBtn");
    var foodName = "";
    var foodDesc = "";
    var imgSrc = "";

    let quantity = parseInt(orderQuantityElement.textContent);
    let price = 0;
    let totalPrice = 0;

    window.ClickedFoodMenu = function(id) {
        quantity = 1;
        orderQuantityElement.textContent = 1;
        const clickedFood = document.getElementById(id);
        var foodNameSpan = clickedFood.querySelector('.FoodNameFont');
        var foodDescSpan = clickedFood.querySelector('.FoodDescFont');
        var foodPriceSpan = clickedFood.querySelector('.FoodPriceFont')
        var imgElement = clickedFood.querySelector("img");
        var foodPrice = foodPriceSpan.textContent;

        imgSrc = imgElement.src;
        foodName = foodNameSpan.textContent;
        foodDesc = foodDescSpan.textContent;
        price = parseInt(foodPrice.replace(/[^0-9]/g, ""), 10)
        OrderBtn.textContent = foodPrice+" 담기";
        modalTitle.textContent = foodName;
        modalBody.textContent = foodDesc;
        modal.style.display = 'block';
    };
    closeModalBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

        minusBtn.style.cursor="default";
        minusBtn.style.color="#dddddd";
        minusBtn.addEventListener('click', () => {
        if(quantity == 2){
        minusBtn.style.color="#dddddd";
        minusBtn.style.cursor="default";
        }
        if (quantity > 1) {
        quantity--;
        orderQuantityElement.textContent = quantity;
        totalPrice = price * quantity ;
        orderBtn.textContent = totalPrice +"원 담기";
        }});
        plusBtn.addEventListener('click', () => {
        minusBtn.style.color="black";
        minusBtn.style.cursor="pointer";
        quantity++;
        orderQuantityElement.textContent = quantity;
        totalPrice = price * quantity ;
        orderBtn.textContent = totalPrice +"원 담기";
        });

        orderBtn.addEventListener('click', () =>{
        const storeName = document.getElementById("storeName").textContent;

        console.log("가게 이름 : "+storeName);
        console.log("메뉴 이름 : "+foodName);
        console.log("수량 : "+quantity);
        console.log("내용 : "+foodDesc)
        console.log("메뉴가격 : "+price);
        console.log("메뉴 이미지 URL : "+imgSrc);

        fetch('/addToCart', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            storeName: storeName,
                            foodName: foodName,
                            quantity: quantity,
                            foodDesc: foodDesc,
                            price: price,
                            imgSrc: imgSrc
                        })
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log('서버 응답:', data);
                        modal.style.display = 'none';
                        const Toast = Swal.mixin({
                            toast: true,
                            showConfirmButton: false,
                            timer: 500,
                            timerProgressBar: true,
                        })
                        Toast.fire({
                            icon: 'success',
                            title: '장바구니에 추가 되었습니다.'
                        })
                    })
                    .catch(error => {
                        console.error('오류 발생:', error);
                    });
    });
});

