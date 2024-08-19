 function checkIdInput(id){
    const input = document.getElementById(id).value;
    const status = document.getElementById("idStatus");
    const waring = document.getElementById("useridWarning");
        if(input.length < 6 || input.length > 12){
        toggleStatusIconCross(status);
    }else{
        toggleStatusIconCheck(status);
    }
  }
  function checkPassInput(id){
        const input = document.getElementById(id).value;
        const status = document.getElementById("passLengthStatus");
        const status1 = document.getElementById("passFormStatus");
        const regex = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$)./;
         if(input.length < 8 || input.length > 20){
          toggleStatusIconCross(status);
        }else{
            toggleStatusIconCheck(status);
        }
        if(regex.test(input)){
            toggleStatusIconCheck(status1);
        }else{
            toggleStatusIconCross(status1);
        }
      }

   function passDoubleCheck(id){
     const input = document.getElementById(id).value;
     const pass = document.getElementById("password").value;
     const status = document.getElementById("passCheckStatus");
     if(pass === input){
        toggleStatusIconCheck(status);
     }else{
        toggleStatusIconCross(status);
     }
   }
    function nameCheck(id){
    const input = document.getElementById(id).value;
    const regex = /^(?![ㄱ-ㅎ]+$)(?![ㅏ-ㅣ]+$)[가-힣]{2,6}$/;
    if(regex.test(input)){
    console.log("올바른 형식임");
    }
    }
    function toggleStatusIconCheck(element){
               element.classList.remove('cross');
               element.classList.add('check');
    }
    function toggleStatusIconCross(element){
               element.classList.remove('check');
               element.classList.add('cross');
    }

    function checkEmail(id){
        var email = document.getElementById("email");
        const emailInput = document.getElementById("emailInput")
        const domainSelect = document.getElementById("emailDomain");
        const domain = domainSelect.value;
        email.value = emailInput.value + "@" + domain;
    }

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
               const input = document.getElementById("phone").value;
               const formattedNumber = formatPhoneNumber(input);
               document.getElementById("phone").value = formattedNumber;
           }
        function addDetailAddress(){
             var address = document.getElementById("address");
             var simpleAddress = document.getElementById("sample3_address").value;
             var detailAddress = document.getElementById("sample3_detailAddress").value;
             address.value = simpleAddress+" "+detailAddress;
        }

        function sample3_execDaumPostcode() {
            // 현재 scroll 위치를 저장해놓는다.
            var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
             // 우편번호 찾기 찾기 화면을 넣을 element
            var element_wrap = document.getElementById('wrap');
                // iframe을 넣은 element를 보이게 한다.
                element_wrap.style.display = 'block';
            new daum.Postcode({
                oncomplete: function(data) {
                    // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
                //
                //     // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                //     if(data.userSelectedType === 'R'){
                //         // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                //         // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                //         if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                //             extraAddr += data.bname;
                //         }
                //         // 건물명이 있고, 공동주택일 경우 추가한다.
                //         if(data.buildingName !== '' && data.apartment === 'Y'){
                //             extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                //         }
                //         // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                //         if(extraAddr !== ''){
                //             extraAddr = ' (' + extraAddr + ')';
                //         }
                //         // 조합된 참고항목을 해당 필드에 넣는다.
                //         document.getElementById("sample3_extraAddress").value = extraAddr;
                //
                //     } else {
                //         document.getElementById("sample3_extraAddress").value = '';
                //     }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('sample3_postcode').value = data.zonecode;
                    document.getElementById("sample3_address").value = addr;
                    var address = document.getElementById("address");
                    var simpleAddress = document.getElementById("sample3_address").value;
                    var detailAddress = document.getElementById("sample3_detailAddress").value;
                    address.value = simpleAddress+" "+detailAddress;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("sample3_detailAddress").focus();

                    // iframe을 넣은 element를 안보이게 한다.
                    // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                    element_wrap.style.display = 'none';

                    // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
                    document.body.scrollTop = currentScroll;
                },
                // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
                onresize : function(size) {
                    element_wrap.style.width = size.width+'px' + size.height+"px";
                },
                width : '100%',
                height: '100%'
            }).embed(element_wrap);
        }
          function foldDaumPostcode() {
          var element_wrap = document.getElementById('wrap');
                    // iframe을 넣은 element를 안보이게 한다.
                    element_wrap.style.display = 'none';
       }