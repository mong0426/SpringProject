document.addEventListener('DOMContentLoaded', function() {
    // 요소 선택
    const addressBtn = document.getElementById("address-btn");
    const addressDiv = document.getElementById("user-address-div");
    const hiddenContents = document.querySelectorAll('.hidden-content');
    const orderListBtn = document.getElementById("order-list-btn");
    const userOrderDiv = document.getElementById("user-order-div");
    const userLikesBtn = document.getElementById("likes-btn");
    const userLikesDiv = document.getElementById("user-likes-div");
    // 지도 관련 변수 선언
    let map, geocoder, marker;

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
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

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

    document.getElementById("search-address-btn").addEventListener("click", openPostcodeSearch);
    document.getElementById("change-address-btn").addEventListener("click", changeAddress);

    // 날짜 포맷팅
    const dateTimeStr = document.getElementById("create-date").textContent;
    if (dateTimeStr) {
        const formattedDate = dateTimeStr.split('T')[0];
        document.getElementById("create-date").textContent = formattedDate;
    }
});
