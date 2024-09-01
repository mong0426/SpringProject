document.addEventListener("DOMContentLoaded", function() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // 섹션 표시 및 숨기기
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
                info.style.display = 'block';
                break;
            case "Review":
                review.style.display = 'block';
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
    });

    // 모달 창 내용 표시
    const modal = document.getElementById('clickedFoodModal');
    const closeModalBtn = document.getElementById('closeModal');
    const modalTitle = document.getElementById("modalTitle");
    const modalBody = document.getElementById("modalBody");
    const orderQuantityElement = document.getElementById('OrderQuantity');
    const minusBtn = document.getElementById('minusBtn');
    const plusBtn = document.getElementById('plusBtn');
    const orderBtn = document.getElementById("OrderBtn");

    let foodName = "";
    let foodDesc = "";
    let imgSrc = "";
    let quantity = 1;
    let price = 0;
    let totalPrice = 0;

    window.ClickedFoodMenu = function(id) {
        const clickedFood = document.getElementById(id);
        const foodNameSpan = clickedFood.querySelector('.FoodNameFont');
        const foodDescSpan = clickedFood.querySelector('.FoodDescFont');
        const foodPriceSpan = clickedFood.querySelector('.FoodPriceFont');
        const imgElement = clickedFood.querySelector("img");
        const foodPrice = foodPriceSpan.textContent;

        imgSrc = imgElement.src;
        foodName = foodNameSpan.textContent;
        foodDesc = foodDescSpan.textContent;
        price = parseInt(foodPrice.replace(/[^0-9]/g, ""), 10);
        orderQuantityElement.textContent = quantity;
        totalPrice = price * quantity;
        orderBtn.textContent = totalPrice + "원 담기";
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

    minusBtn.addEventListener('click', () => {
        if (quantity > 1) {
            quantity--;
            orderQuantityElement.textContent = quantity;
            totalPrice = price * quantity;
            orderBtn.textContent = totalPrice + "원 담기";
        }
    });

    plusBtn.addEventListener('click', () => {
        quantity++;
        orderQuantityElement.textContent = quantity;
        totalPrice = price * quantity;
        orderBtn.textContent = totalPrice + "원 담기";
    });

    orderBtn.addEventListener('click', () => {
        const storeName = document.getElementById("storeName").textContent;

        fetch('/addToCart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
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
            if(data.success){
            const container = document.getElementById("notification");
            if (data.cartItemsSize === 1) {
                const span = document.createElement('span');
                span.className = 'notification-bubble';
                container.appendChild(span);
            }
            const notification = document.querySelector(".notification-bubble");
            notification.textContent = data.cartItemsSize;
            modal.style.display = 'none';

            const Toast = Swal.mixin({
                toast: true,
                showConfirmButton: false,
                timer: 500,
                timerProgressBar: true,
            });
            Toast.fire({
                icon: 'success',
                title: '장바구니에 추가 되었습니다.'
            });
           }else if(data.otherStore){
Swal.fire({
    title: '장바구니엔 같은 가게의 메뉴만 담을 수 있습니다.',
    text: '선택하신 메뉴를 장바구니에 담을 경우 이전에 담은 메뉴가 삭제됩니다.',
    showCancelButton: true,
    confirmButtonText: '담기',
    cancelButtonText: '취소'
}).then((result) => {
    if (result.isConfirmed) {
        fetch('/addToCart/otherStore', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
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
            if (data.error) {
                Swal.fire({
                    title: '오류',
                    text: data.error,
                    icon: 'error'
                });
            } else if (data.success) {
                Swal.fire({
                    title: '성공',
                    text: data.success,
                    icon: 'success'
                });
            const container = document.getElementById("notification");
            const notification = document.querySelector(".notification-bubble");
            notification.textContent = "1";
            modal.style.display = 'none';
            }
        })
        .catch(error => {
            Swal.fire({
                title: '오류',
                text: '서버 요청 중 오류가 발생했습니다.',
                icon: 'error'
            });
        });
    } else if (result.dismiss === Swal.DismissReason.cancel) {  // 사용자가 "취소"를 선택한 경우
        Swal.fire({
            title: '취소됨',
            text: '장바구니 추가가 취소되었습니다.',
            icon: 'info'
        });
       modal.style.display = 'none';
    }
});
}
});
});

    // 이미지 미리보기
    document.getElementById('multipartFile').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const image = document.getElementById('imagePreview');
                image.src = e.target.result;
                image.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });

    // 좋아요 버튼 상태 처리
    const likesBtn = document.getElementById("LikesBtn");
    const statusInput = document.getElementById("isLikedStore");
    const likeIcon = document.getElementById('likeIcon');
    const sno = document.getElementById('sno').value;
    let likeCount = document.getElementById("user-likes").textContent;

    // 상태에 따라 아이콘 이미지 설정
    function updateLikeIcon() {
        const status = statusInput.value === 'true'; // 'true' 문자열로 변환하여 불리언으로 사용
        likeIcon.src = status ? "/img/storedetailimg/LikesIcon.png" : "/img/storedetailimg/LikesIconBefore.png";
    }

    // 페이지 로드 시 아이콘 업데이트
    updateLikeIcon();

    likesBtn.addEventListener("click", function() {
        const status = statusInput.value === 'true';
        const newStatus = !status;

        fetch('/change-like', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                likes: newStatus,
                sno: sno,
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) { // 서버에서 반환한 성공 여부에 따라 처리
                document.getElementById("user-likes").textContent = data.likes;

                statusInput.value = newStatus; // 상태 업데이트
                updateLikeIcon(); // 아이콘 업데이트
            } else {
                console.error('서버에서 오류 발생');
            }
        })
        .catch(error => {
            console.error('오류 발생:', error);
            window.location.href = "login.html";
        });
    });

function addPageBtn(){
document.querySelectorAll('.page-link').forEach(item => {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        const activePageLink = document.querySelector('.pagination .page-item.active .page-link');
        const pageNumber = this.getAttribute('href').split('=')[1];
        const storeName = document.getElementById("storeName").textContent;
        console.log("pageNumber=================="+pageNumber);
        fetch('/review/page', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                page: parseInt(pageNumber),
                store : storeName
            })
        })
        .then(response => response.json())
        .then(data => {
            updateReviews(data.reviews);
            updatePagination(data.currentPage, data.totalPages);
        })
        .catch(error => console.error('Error:', error));
    });
});
}
addPageBtn();
function updateReviews(reviews) {
    const container = document.getElementById('reviews-container');
    container.innerHTML = '';

  reviews.forEach(review => {
         const reviewDiv = document.createElement('div');
         reviewDiv.className = 'FoodMenuDiv';
         reviewDiv.id = 'FoodMenuDiv' + review.id;

         // Generate star images
         let starsHtml = '';
         const filledStars = Math.floor(review.rating);
         const emptyStars = 5 - filledStars;

         for (let i = 0; i < filledStars; i++) {
             starsHtml += '<img class="rating-star" src="/img/storedetailimg/star-filled.png" alt="Star"/>';
         }
         for (let i = 0; i < emptyStars; i++) {
             starsHtml += '<img class="rating-star" src="/img/storedetailimg/star-empty.png" alt="Star"/>';
         }

         reviewDiv.innerHTML = `
             <div class="FoodContentDiv">
                 <span class="FoodNameFont">${review.writer}</span><br/>
                 <span>${review.title}</span><br/>
                 <div class="rating-star-div">
                     <span style="margin-right:10px;">${review.rating}.0</span>
                     ${starsHtml}<br/>
                 </div>
                 <p>${review.content}</p>
             </div>
         `;
        container.appendChild(reviewDiv);
    });
}

function updatePagination(currentPage, totalPages) {
        const paginationContainer = document.querySelector('.pagination');
        paginationContainer.innerHTML = '';

        const prevDisabled = currentPage === 0 ? 'disabled' : '';
        paginationContainer.innerHTML += `
            <li class="page-item ${prevDisabled}">
                <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
            </li>
        `;
        for (let i = 0; i < totalPages; i++) {
            const activeClass = i === currentPage ? 'active' : '';
            paginationContainer.innerHTML += `
                <li class="page-item ${activeClass}">
                    <a class="page-link" href="?page=${i}">${i + 1}</a>
                </li>
            `;
        }
        const nextDisabled = currentPage + 1 === totalPages ? 'disabled' : '';
        paginationContainer.innerHTML += `
            <li class="page-item ${nextDisabled}">
                <a class="page-link" href="?page=${currentPage + 1}">Next</a>
            </li>
        `;
        addPageBtn();
    }
});