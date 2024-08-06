    document.addEventListener("DOMContentLoaded", function() {              // 음식메뉴 , 가게 정보 , 리뷰 스크립트
        const foodMenuBtn = document.getElementById("FoodMenuBtn");
        const storeInfoBtn = document.getElementById("StoreInfoBtn");
        const reviewBtn = document.getElementById("ReviewBtn");
        const hiddenContents = document.querySelectorAll('.HiddenContent');
        const menus = document.querySelectorAll('.FoodMenuDiv');
        const info = document.getElementById("StoreInfoDiv");
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
    });

document.addEventListener('DOMContentLoaded', function() {      //모달 내용표시 스크립트
    const modal = document.getElementById('clickedFoodModal');
    const closeModalBtn = document.getElementById('closeModal');
    const modalTitle = document.getElementById("modalTitle");
    const modalBody = document.getElementById("modalBody");
    const orderCountElement = document.getElementById('OrderCount');
    const minusBtn = document.getElementById('minusBtn');
    const plusBtn = document.getElementById('plusBtn');
    const orderBtn = document.getElementById("OrderBtn");
    let count = parseInt(orderCountElement.textContent);
    let price = parseInt(orderBtn.textContent.replace(/[^0-9]/g, ""), 10);
    minusBtn.style.cursor="default";
    minusBtn.style.color="#dddddd";
    minusBtn.addEventListener('click', () => {
    console.log(orderBtn.textContent);
    if(count == 2){
    minusBtn.style.color="#dddddd";
    minusBtn.style.cursor="default";
    }
    if (count > 1) {
    count--;
    orderCountElement.textContent = count;
    }});
    plusBtn.addEventListener('click', () => {
    minusBtn.style.color="black";
    minusBtn.style.cursor="pointer";
    count++;
    orderCountElement.textContent = count;
    });

    window.ClickedFoodMenu = function(id) {
        const clickedFood = document.getElementById(id);
        var foodNameSpan = clickedFood.querySelector('.FoodNameFont');
        var foodDescSpan = clickedFood.querySelector('.FoodDescFont');
        var foodPriceSpan = clickedFood.querySelector('.FoodPriceFont')

        var foodName = foodNameSpan.textContent;
        var foodDesc = foodDescSpan.textContent;
        var foodPrice = foodPriceSpan.textContent;

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
});

