import { Link } from "react-router-dom";
import styled from "styled-components";

// 백-프론트 연결 관련 import
import API from "../../config";
import { useState, useEffect } from "react";

// mui import
// 이용하려는 것: https://mui.com/material-ui/react-card/#media

// 무한 스크롤 구현 라이브러리
// https://www.npmjs.com/package/react-intersection-observer
import React from "react";
import { useInView } from "react-intersection-observer";

function VocaStore() {
  // 프론트 - 백 통신 설정
  const [bookmark, setBookmark] = useState<any>([]);

  // 토큰을 로컬 스토리지에 저장
  localStorage.setItem(
    "token",
    "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFYXN5U2lnbiIsImV4cCI6MTcwNzI5NDUwMiwiaWQiOjYsImxvZ2luSWQiOiJzc2FmeSJ9.hhi2vy9Kx0Uo2J617VLjV-3e10OVxhtAgIecFxrX1o2cfBCH1ZUd5MShJ2yk8WFfwcsy7FFoN0wsZ5hft5E5hg"
  );

  // 로컬 스토리지에서 토큰을 가져옴
  const token = localStorage.getItem("token") || "";

  const getBookmark = async () => {
    const response = await fetch(`${API.BOOKMARK}`, {
      method: "GET",
      headers: {
        Authorization: token,
      },
    });
    const json = await response.json();
    setBookmark(json);
    console.log(response);
  };

  useEffect(() => {
    getBookmark();
  }, []);

  // css 관련

  const Container = styled.div`
    min-height: 120vh;
    width: 70vw;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: skyblue;
  `;

  //   const { ref, inView, entry } = useInView(options);

  return (
    <Container>
      {/* 임시로 넣어놓은 모달 테스트 링크 */}
      <Link to={"/store_test"}>모달 테스트</Link>;<br></br>
      {bookmark.map((item: any) => (
        <div key={item.signId}>
          <p>Sign ID: {item.signId}</p>
          <p>Content: {item.content}</p>
          <img
            src={item.imagePath}
            alt={item.content}
            width="100px"
            height="100px"
          />
          {/* 비디오 등 추가적인 정보도 여기에 렌더링할 수 있음 */}
        </div>
      ))}
    </Container>
  );
}
export default VocaStore;
