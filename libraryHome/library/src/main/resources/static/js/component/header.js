class HeaderService {
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new HeaderService();
        }
        return this.#instance;
    }

    loadHeader() {
        const headerContainer = document.querySelector(".header-container");
        const principal = PrincipalApi.getInstance().getPrincipal();

        headerContainer.innerHTML=`
            <h1 class="brand-logo"><a href="/index">시립 도서관</a></h1>
            <ul class="menu-container">
                <li><a href="">도서관이용안내</a></li>
                <li><a href="/search">자료검색·이용</a></li>
                <li><a href="">참여마당</a></li>
                <li><a href="">독서공감</a></li>
                <li><a href="">책으로 행복한 청주</a></li>
                <li><a href="">도서관소개</a></li>
                <li><a href="">작은도서관</a></li>
                <li><a href="">나의도서관</a></li>
            </ul>
            <ul class="account-container">
                ${principal == null 
                    ? `
                    <li><a href="/account/login">로그인</a></li>
                    <li><a href="/account/register">회원가입</a></li>
                    `
                    :`
                    <li><a href="/mypage">${principal.user.name}</a></li>
                    <li><a href="/logout">로그아웃</a></li>
                    `
                }
            </ul>
        `;
    }
}