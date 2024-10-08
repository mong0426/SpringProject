document.addEventListener('DOMContentLoaded', function() {
    // 요소 선택
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const addressBtn = document.getElementById("address-btn");
    const addressDiv = document.getElementById("user-address-div");
    const hiddenContents = document.querySelectorAll('.hidden-content');
    const orderListBtn = document.getElementById("order-list-btn");
    const userOrderDiv = document.getElementById("user-order-div");
    const userLikesBtn = document.getElementById("likes-btn");
    const userLikesDiv = document.getElementById("user-likes-div");
    const userInfoBtn = document.getElementById("user-info-btn");
    const userInfoDiv = document.getElementById("user-info-div");
    const Toast = Swal.mixin({
          toast: true,
          showConfirmButton: false,
          timer: 800,
          timerProgressBar: true,
     });

    // 지도 관련 변수 선언
    let map, geocoder, marker;

     window.onload = function() {
                // URL에서 파라미터 확인
                const urlParams = new URLSearchParams(window.location.search);
                const likeStores = urlParams.get('likeStores');

                // likeStores 파라미터가 true라면 해당 요소 클릭
                if (likeStores === 'true') {
                    const likeButton = document.getElementById("likes-btn");
                    if (likeButton) {
                        likeButton.click();  // 자동 클릭
                    }
                }
     }
    // 날짜 포맷팅
    const dateTimeStr = document.getElementById("create-date").textContent;
    if (dateTimeStr) {
        const formattedDate = dateTimeStr.split('T')[0];
        document.getElementById("create-date").textContent = formattedDate + " 일";
    }

    // 지도 및 주소 검색 초기화
    function initializeMap() {
        map = new daum.maps.Map(document.getElementById('map'), {
            center: new daum.maps.LatLng(37.537187, 127.005476),
            level: 5
        });

        geocoder = new daum.maps.services.Geocoder();
        marker = new daum.maps.Marker({
            position: new daum.maps.LatLng(37.537187, 127.005476),
            map: map
        });
    }

    // 주소 변경 후 지도 업데이트
    function updateMapWithAddress(address) {
        if (geocoder) { // geocoder가 정의되어 있는지 확인
            geocoder.addressSearch(address, function(results, status) {
                if (status === daum.maps.services.Status.OK) {
                    const result = results[0];
                    const coords = new daum.maps.LatLng(result.y, result.x);
                    map.setCenter(coords);
                    marker.setPosition(coords);
                    document.getElementById("user-address").value = address;
                }
            });
        }
    }

    // 주소 검색 버튼 클릭 시
    function openPostcodeSearch() {
        new daum.Postcode({
            oncomplete: function(data) {
                const addr = data.address;
                updateMapWithAddress(addr);
            }
        }).open({
            left: (window.screen.width / 2) - 250,
            top: (window.screen.height / 2) - 300
        });
    }

    // 주소 변경 버튼 클릭 시
    function changeAddress() {
        const inputAddress = document.getElementById("user-address").value;
        const inputDetailAddress = document.getElementById("user-detail-address").value;
        const fullAddress = inputAddress + ' ' + inputDetailAddress;
        if (inputDetailAddress) {
            fetch('/changeAddress', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify({
                    address: fullAddress
                })
            })
            .then(response => response.json())
            .then(data => {
                Swal.fire({
                    icon: 'success',
                    title: '주소가 변경 되었습니다.',
                    toast: true,
                    showConfirmButton: false,
                    timer: 500,
                    timerProgressBar: true
                });
            })
            .catch(error => {
                console.error('오류 발생:', error);
            });
        }
    }

    orderListBtn.addEventListener("click", function(){
        document.querySelectorAll('.sub-menu-div').forEach(btn => btn.classList.remove('SelectMenu'));
        this.classList.add('SelectMenu');
        hiddenContents.forEach(hiddenContent => hiddenContent.style.display = 'none');
        userOrderDiv.style.display = 'block';
    });
    userLikesBtn.addEventListener("click", function(){
            document.querySelectorAll('.sub-menu-div').forEach(btn => btn.classList.remove('SelectMenu'));
            this.classList.add('SelectMenu');
            hiddenContents.forEach(hiddenContent => hiddenContent.style.display = 'none');
            userLikesDiv.style.display = 'block';
        });
    // 이벤트 리스너 등록
    if(addressBtn != null){
    addressBtn.addEventListener("click", function() {
        const inputAddress = document.getElementById("user-address").value;
        document.querySelectorAll('.sub-menu-div').forEach(btn => btn.classList.remove('SelectMenu'));
        this.classList.add('SelectMenu');
        hiddenContents.forEach(hiddenContent => hiddenContent.style.display = 'none');
        addressDiv.style.display = 'block';

        // 지도 초기화 및 주소 업데이트
        if (!map) {
            initializeMap(); // 지도 초기화
        }
        updateMapWithAddress(inputAddress);
    });
    }
    userInfoBtn.addEventListener("click",function(){
        document.querySelectorAll('.sub-menu-div').forEach(btn => btn.classList.remove('SelectMenu'));
        this.classList.add('SelectMenu');
        hiddenContents.forEach(hiddenContent => hiddenContent.style.display = 'none');
        userInfoDiv.style.display = 'block';
    });

    document.getElementById("search-address-btn").addEventListener("click", openPostcodeSearch);
    document.getElementById("change-address-btn").addEventListener("click", changeAddress);

   function checkPassInput() {
          const inputPass = document.getElementById("password").value;
          const inputPassCheck = document.getElementById("passCheck").value;
          const statusPassCheck = document.getElementById("passCheckStatus");
          const validWaring = document.getElementById("valid-warning");
          statusPassCheck.style.display= "block";
          // 비밀번호와 비밀번호 확인 필드가 모두 채워졌는지 확인
          if (inputPass && inputPassCheck && inputPass === inputPassCheck) {
                  toggleStatusIconCheck(statusPassCheck);
                  validWaring.textContent = "비밀번호가 일치합니다.";
                  document.getElementById("pass-check-status").value = "true";
          } else {
                  toggleStatusIconCross(statusPassCheck);
                  validWaring.textContent = "비밀번호를 다시 입력해주세요.";
                  document.getElementById("pass-check-status").value = "false";
                  }
    }
      // 상태 아이콘을 체크로 설정
      function toggleStatusIconCheck(element) {
          element.classList.remove('cross');
          element.classList.add('check');
      }

      // 상태 아이콘을 크로스로 설정
      function toggleStatusIconCross(element) {
          element.classList.remove('check');
          element.classList.add('cross');
      }
      // 비밀번호 입력 필드가 변경될 때마다 checkPassInput 호출
      document.getElementById("password").addEventListener("input", checkPassInput);
      document.getElementById("passCheck").addEventListener("input", checkPassInput);

 function formatPhoneNumber(value) {
        // 모든 비숫자 문자 제거
        value = value.replace(/\D/g, '');
        // 길이에 따라 포맷팅
        if (value.length <= 3) {
            return value;
        } else if (value.length <= 7) {
            return value.slice(0, 3) + '-' + value.slice(3);
        } else {
            return value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11);
        }
    }

    function updatePhoneNumber() {
        const input = document.getElementById("phone");
        const formattedNumber = formatPhoneNumber(input.value);
        input.value = formattedNumber;
    }

    const phoneInput = document.getElementById("phone");
    phoneInput.addEventListener('input', updatePhoneNumber);

function validateForm() {
    const name = document.getElementById("userName").value;
    const password = document.getElementById("password").value;
    const passCheck = document.getElementById("passCheck").value;
    const email = document.getElementById("userEmail").value;
    const phone = document.getElementById("phone").value;
    const passCheckStatus = document.getElementById("passCheckStatus");
    const isCorrectPass = document.getElementById("pass-check-status").value;
    let isValid = true;
    // 이름 유효성 검사
    const nameRegex = /^(?![ㄱ-ㅎ]+$)(?![ㅏ-ㅣ]+$)[가-힣]{2,6}$/;
    if (!nameRegex.test(name)) {
        isValid = false;
    }
    // 비밀번호 확인 유효성 검사
    if (isCorrectPass === "false") {
        isValid = false;
    }

    // 이메일 유효성 검사
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        isValid = false;
    }

    // 전화번호 유효성 검사
    const phonePattern = /^\d{3}-\d{4}-\d{4}$/;
    if (!phonePattern.test(phone)) {
        isValid = false;
    }
    return isValid;
}

    document.getElementById("change-user-info-btn").addEventListener('click', function(event) {
        event.preventDefault(); // 기본 폼 제출 방지
        if (validateForm()) {
            const form = document.getElementById("edit-user-form");
            const formData = new FormData(form);
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

            fetch(form.action, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // 성공적인 응답 처리
              Toast.fire({
                      icon: 'success',
                      title: '회원 정보가 수정되었습니다.'
                   });
                } else {
                    // 서버 오류 처리
              Toast.fire({
                icon: 'error',
                title: '정보 수정에 실패했습니다.'
              });
              }
            })
            .catch(error => {
                console.error('오류 발생:', error);
                alert("오류 발생. 다시 시도해 주세요.");
            });
        }else{
          Toast.fire({
                       icon: 'warning',
                       title: '입력값이 올바르지 않습니다.'
                });
        }
       });

       const modal = document.getElementById('delete-account-modal');
       const closeModalBtn = document.getElementById('closeModal');
       const cancelBtn = document.getElementById('cancel-btn');
       const openModalBtn = document.getElementById("openModal");
       closeModalBtn.addEventListener('click', function() {
            modal.style.display = 'none';
          });
       cancelBtn.addEventListener('click', function() {
                  modal.style.display = 'none';
                });
       openModalBtn.addEventListener("click",function(){
            modal.style.display = 'block';
       });
       const deleteBtn = document.getElementById("delete-account-btn");

        deleteBtn.addEventListener("click",function(){
        const pass = document.getElementById("delete-passCheck").value;
        fetch('/delete/account', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                password: pass
            })
        })
        .then(response => response.json())
        .then(data => {
            // 서버 응답 처리
            if (data.success) {
           Swal.fire({
               icon: 'success',
               title: '탈퇴되었습니다.',
               text: '메인페이지로 이동합니다.',
               timer: 2000,
               timerProgressBar: true, //
               willClose: () => {
                   window.location.href = '/';
               }
           });
            } else {
                 Toast.fire({
                      icon: 'warning',
                      title: '비밀번호가 틀렸습니다.'
                  });
            }
        })
        .catch(error => {
            console.error('에러 발생:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
    });
    document.querySelectorAll('.review-btn').forEach(button => {
    button.addEventListener('click', function() {
    const store = this.getAttribute('data-store');
    const food = this.getAttribute('data-food');
    const modal = document.getElementById("add-review-modal");
    const closeModalBtn = document.getElementById('btn-close');
    const modalTitle = document.getElementById("add-review-modal-title")
    const modalBody = document.getElementById('add-review-modal-body');
    const modalHeader = document.getElementById('add-review-modal-header');
    const ratingValue = this.getAttribute('data-rating');
    const stars = document.querySelectorAll('.rating-star');
    const ono = this.getAttribute('data-orderHistory');
    document.getElementById("review-store").value = store;
    document.getElementById("review-food").value = food;
    document.getElementById("orderHistory-ono").value = ono;

    document.getElementById("review-rating-div").style.display = 'flex';
    modalTitle.innerHTML = ``;
    modalBody.innerHTML = `
    <div class="review-body-span">
        <span>${store}</span>의 음식은 어떠셨나요?
    <div>
    `;

    stars.forEach(star => {
         star.addEventListener('click', function() {
             // 클릭된 별의 data-rating 값을 가져옴
             const rating = this.getAttribute('data-rating');
             document.getElementById("review-rating").value = rating;
             modalTitle.innerHTML = `
                            <input class="review-title-input" type="text" id="reviewTitle" name="title" placeholder="제목을 입력해주세요." required>
             `;
             document.getElementById("review-rating-div").style.display = 'none';
             modalBody.innerHTML = ``;
             modalBody.innerHTML = `
               <div style="width: 100%; height: 300px; padding: 20px;">
                <textarea class="review-content-input" id="reviewContent" name="content" placeholder="내용을 입력해주세요." required></textarea>
               </div>
               <div>
                <button class="review-btn" id="review-submit-btn">리뷰등록</button>
               </div>
             `;
     });
    });
    modal.style.display = 'block';

    closeModalBtn.addEventListener('click', function() {
            modal.style.display = 'none';
     });
    });
   });
});