const host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();
    const loginBtn = $(document).getElementById("login-btn");
    const signupBtn = $(document).getElementById("signup-btn");
    const mypageBtn = $(document).getElementById("mypage-btn");
    const logoutBtn = $(document).getElementById("logout-btn");

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
        loginBtn.disabled = false;
        signupBtn.disabled = false;
        mypageBtn.disabled = true;
        logoutBtn.disabled = true;
    } else {
        loginBtn.disabled = true;
        signupBtn.disabled = true;
        mypageBtn.disabled = false;
        logoutBtn.disabled = false;
    }
});


function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/api/user/login-page';
}

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }

    return auth;
}