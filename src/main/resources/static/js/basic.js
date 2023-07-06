// // <script src="https://code.jquery.com/jquery-3.7.0.min.js"
// //         integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossOrigin="anonymous"></script>
//
// const host = 'http://' + window.location.host;
//
// // $(document).ready(function () {
// //     const auth = getToken();
// //
// //     if (auth !== undefined && auth !== '') {
// //         $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
// //             jqXHR.setRequestHeader('Authorization', auth);
// //         });
// //     } else {
// //         return;
// //     }
// // });
//
//
// function logout() {
//     // 토큰 삭제
//     Cookies.remove('Authorization', {path: '/'});
//     window.location.href = host;
// }
//
// function getToken() {
//     let auth = Cookies.get('Authorization');
//
//     if(auth === undefined) {
//         return '';
//     }
//
//     return auth;
// }
