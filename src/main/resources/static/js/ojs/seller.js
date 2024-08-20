 function checkIdInput(id){
    const input = document.getElementById(id).value;
    const status = document.getElementById("idStatus");
    const waring = document.getElementById("useridWarning");
    document.getElementById("isExistCheck").value = "false";
    if(input.length < 6 || input.length > 12){
        toggleStatusIconCross(status);
    }else{
        toggleStatusIconCheck(status);
    }
  }
  function checkPassInput(id){
          const inputPass = document.getElementById("password").value;
          const inputPassCheck = document.getElementById("passCheck").value;
          const status1 = document.getElementById("passLengthStatus");
          const status2 = document.getElementById("passFormStatus");
          const statusPassCheck = document.getElementById("passCheckStatus");
          const regex = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$)./;
           if(inputPass.length < 8 || inputPass.length > 20){
            toggleStatusIconCross(status1);
          }else{
              toggleStatusIconCheck(status1);
          }
          if(regex.test(inputPass)){
              toggleStatusIconCheck(status2);
          }else{
              toggleStatusIconCross(status2);
          }
          if((inputPass != "" || inputPassCheck != "") && inputPass === inputPassCheck){
              toggleStatusIconCheck(statusPassCheck);
           }else{
              toggleStatusIconCross(statusPassCheck);
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

      function updatePhoneNumber() {
             const first = document.getElementById("telFirst");
             const second = document.getElementById("telSecond");
             const last = document.getElementById("telLast");
             var tel = first.value + '-' + second.value + '-' + last.value;

             document.getElementById("tel").value = tel;

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
               document.addEventListener("DOMContentLoaded", function() {
               document.getElementById("createForm").addEventListener("submit", function(event) {
               const inputPass = document.getElementById("password");
               const inputPassCheck = document.getElementById("passCheck");
               const addressCheck = document.getElementById("sample3_address");
               const Toast = Swal.mixin({
                     toast: true,
                     showConfirmButton: false,
                     timer: 800,
                     timerProgressBar: true
               });
                if(document.getElementById("isExistCheck").value != "true"){
                    event.preventDefault();
                    Toast.fire({
                         icon: 'warning',
                        title: '아이디 중복체크 오류입니다.'
                    });
                }
                if(inputPass.value != inputPassCheck.value){
               event.preventDefault();
               inputPassCheck.focus();
               Toast.fire({
                        icon: 'warning',
                        title: '비밀번호를 다시 한 번 정확히 입력해주세요.'
                  });
               }
               if(addressCheck.value == ""){
               event.preventDefault();
               addressCheck.focus();
                Toast.fire({
                        icon: 'warning',
                        title: '주소 검색을 해주세요.'
                });
               }
           });
           });
          $(document).ready(function() {
             $('#isIdExistBtn').click(function() {
              const inputId = document.getElementById("id").value;
              const isIdExistBtn = document.getElementById("isIdExistBtn");
              const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
              const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
              const Toast = Swal.mixin({
              toast: true,
              showConfirmButton: false,
              timer: 800,
              timerProgressBar: true
          });
          fetch('/isExist/id', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
                  [csrfHeader]: csrfToken
              },
              body: JSON.stringify({ userid: inputId })
          }) .then(response => response.json())
             .then(data => {
              console.log('Response data:', data);
             if(inputId != ""){
              if (data === false) {
              document.getElementById("isExistCheck").value = "true";
                  Toast.fire({
                      icon: 'success',
                      title: '사용 가능한 아이디입니다.'
                  });
              } else {
                  Toast.fire({
                      icon: 'error',
                      title: '이미 사용 중인 아이디입니다.'
                  });
              }}
          })
          .catch(error => {
              console.error('Error:', error);
          });
      });
  });