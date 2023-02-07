<%--
  ~ Copyright (C) 23. 2. 7. 오후 11:51 Ahngbeom (https://github.com/Ahngbeom)
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <div class="container-fluid g-2 h-auto ">
            <div class="d-flex justify-content-between">
                <div class="col" id="currentTime">
                </div>
                <div class="col text-end">
                    <div>
                        <div class="d-flex justify-content-end align-items-center gap-2" id="loginForm">
                            <label>
                                <input type="text" name="username" class="form-control form-control-sm"
                                       placeholder="ID">
                            </label>
                            <label>
                                <input type="password" name="password" class="form-control form-control-sm"
                                       placeholder="Password">
                            </label>
                            <button type="button" id="loginBtn" class="btn btn-sm btn-outline-secondary">Login</button>
                        </div>
                        <div id="authentication" class="d-flex justify-content-end gap-2 visually-hidden">
                            <%--                            <div id="jwtInfo">--%>
                            <%--                                <div id="accessTokenValidity">--%>
                            <%--                                    <label>--%>
                            <%--                                        Access Token:--%>
                            <%--                                    </label>--%>
                            <%--                                    <span id="accessTokenValidityTimeLeft"></span>--%>
                            <%--                                </div>--%>
                            <%--                                <div id="refreshTokenValidity">--%>
                            <%--                                    <label>--%>
                            <%--                                        Refresh Token:--%>
                            <%--                                    </label>--%>
                            <%--                                    <span id="refreshTokenValidityTimeLeft"></span>--%>
                            <%--                                </div>--%>
                            <%--                            </div>--%>
                            <div>
                                <span id="authenticatedUsername"></span>
                                <button type="button" id="logoutBtn" class="btn btn-sm btn-warning">Logout</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="d-flex justify-content-end gap-1 border border-light" id="additionalButtons">
                <button type='button' class='btn btn-sm btn-outline-secondary' id='accountPreferences'>회원 정보</button>
            </div>

            <div class="d-flex gap-2 m-3 h-auto">
                <div class="d-flex flex-column col-3 border border-dark gap-3" id="boardList">
                    <div class="d-flex flex-wrap justify-content-between">
                        <label class="h5">
                            게시판
                        </label>
                        <div id="boardControlButtonsOnSideBar">
                        </div>
                    </div>
                    <div>
                        <button type="button" class="btn btn-sm btn-link board-title">전체 게시물</button>
                        <ul>
                        </ul>
                    </div>

                </div>
                <div class="d-flex flex-column col-9 gap-2 h-auto" id="mainContents">
                    <div id="additionalArea" class="border border-dark visually-hidden">
                    </div>
                    <div id="postForm" class="border border-info p-2 gap-2 visually-hidden">
                        <div class="d-flex justify-content-end p-0">
                            <button type="button" class="btn-close" aria-label="Close"></button>
                        </div>
                        <div class="d-flex justify-content-between">
                            <div class="d-flex col-8">
                                <div class="form-floating mb-3">
                                    <input id="postRegDate" class="form-control-sm form-control-plaintext" readonly/>
                                    <label for="postRegDate">Posted on</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input id="postUpdateDate" class="form-control-sm form-control-plaintext" readonly/>
                                    <label for="postUpdateDate">Updated on</label>
                                </div>
                            </div>
                            <div class="d-flex col-4">
                                <div class="form-floating mb-3 col-4">
                                    <input id="postViews" class="form-control-sm form-control-plaintext" readonly/>
                                    <label for="postViews">Views</label>
                                </div>
                                <button id="postRecommend" class="btn btn-outline-info col-4">👍<span></span></button>
                                <button id="postNotRecommend" class="btn btn-outline-danger col-4">👎<span></span>
                                </button>
                            </div>
                        </div>
                        <div class="form-floating mb-3">
                            <select name="boardNo" class="form-select w-50"
                                    aria-label="Default select example" disabled>
                            </select>
                            <label>Board where this post was registered</label>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <div class="form-floating">
                                <input id="postTitle" class="form-control-plaintext h4"/>
                                <label for="postTitle">Title</label>
                            </div>
                            <div class="form-floating col-3">
                                <select name="postType" class="form-select" disabled>
                                    <option value="NOTICE">공지</option>
                                    <option value="NORMAL">일반</option>
                                </select>
                                <label>Post Type</label>
                            </div>
                        </div>
                        <div class="form-floating">
                            <textarea class='form-control-plaintext _min-vh-100' id="postContents">
                            </textarea>
                            <label for="postContents">Contents</label>
                        </div>
                        <div class="d-flex justify-content-end gap-1 mt-2">
                            <button type="button" id="postCreateBtn"
                                    class="btn btn-outline-info rounded-0 visually-hidden">게시
                            </button>
                            <button type="button" id="showPostUpdateFormBtn"
                                    class="btn btn-outline-warning rounded-0 visually-hidden">수정
                            </button>
                            <button type="button" id="postRemoveBtn"
                                    class="btn btn-outline-danger rounded-0 visually-hidden">삭제
                            </button>
                        </div>
                    </div>
                    <div class="border border-success">
                        <div id="postsByBoard">
                            <div class="d-flex justify-content-between">
                                <div>
                                    <label class="h4" id="postsByBoardTitle">
                                    </label>
                                    <span id="postCount"></span>
                                </div>
                                <div id="boardControlButtonsOnMain">
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <button type="button" class="btn btn-info rounded-0" id="showPostCreateFormBtn">게시물 작성
                                </button>
                            </div>
                            <ul id="postList">
                            </ul>
                            <nav class="d-flex justify-content-center visually-hidden" id="paginationNav">
                                <ul class="pagination">
                                    <li class="page-item">
                                        <button class="page-link disabled">Previous</button>
                                    </li>
                                    <li class="page-item">
                                        <button class="page-link">Next</button>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>