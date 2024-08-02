    document.addEventListener("DOMContentLoaded", function() {              // 음식메뉴 , 가게 정보 , 리뷰 스크립트
        const foodMenuBtn = document.getElementById("FoodMenuBtn");
        const storeInfoBtn = document.getElementById("StoreInfoBtn");
        const reviewBtn = document.getElementById("ReviewBtn");
        const hiddenContents = document.querySelectorAll('.HiddenContent');
        const menus = document.querySelectorAll('.FoodMenuDiv');
        const info = document.getElementById("StoreInfoDiv");
        function handleClick(section) {
            console.log(`Clicked on ${section}`);
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
                    // Add your logic for the "리뷰" button here
                    break;
            }
        }

        // Attach event listeners to each button
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

document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('clickedFoodModal');
    const closeModalBtn = document.getElementById('closeModal');

    window.ClickedFoodMenu = function(id) {
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