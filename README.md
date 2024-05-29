<!-- PROJECT LOGO -->
<div align="center">
  <a href="https://github.com/osamhack2021/app_web_dronai_62bn">
    <img
      src="https://github.com/U2DJ2/moim-today/assets/36218321/b48b271e-2c51-4c0e-913c-92fb5788c267.png"
      alt="Logo" width="256px" height="200px">
  </a>

  <h3 align="center">Moim Today</h3>

  <p align="center">
    대학별 시간표 기반 온라인 캘린더 및 일정 관리 서비스
    <br />
    <br />
    <a href="https://moim.today">Web Demo</a>
    ·
    <a href="https://docs.google.com/presentation/d/1gOtEuXTCLChtCmJZqL4Rpi08TlMlSRpf/edit?usp=sharing&ouid=118304770497693886763&rtpof=true&sd=true">Presentation</a>
    <br />
    <br />
    <a href="https://github.com/U2DJ2/moim-today/graphs/contributors">
      <img src="https://img.shields.io/github/contributors/U2DJ2/moim-today.svg?style=for-the-badge" />
    </a>
    <a href="https://github.com/U2DJ2/moim-today/network/members">
      <img src="https://img.shields.io/github/forks/U2DJ2/moim-today.svg?style=for-the-badge" />
    </a>
    <a href="https://github.com/U2DJ2/moim-today/stargazers">
      <img src="https://img.shields.io/github/stars/U2DJ2/moim-today.svg?style=for-the-badge" />
    </a>
    <a href="https://github.com/U2DJ2/moim-today/issues">
      <img src="https://img.shields.io/github/issues/U2DJ2/moim-today.svg?style=for-the-badge" />
    </a>
    <a href="https://github.com/U2DJ2/moim-today/blob/master/license.md">
      <img src="https://img.shields.io/github/license/U2DJ2/moim-today.svg?style=for-the-badge" />
    </a>
  </p>
</div>
<h1 id="service_introduction"> 1. 팀플, 동아리 일정 및 개인 일정 관리 서비스, Moim-Today </h1>
<h2>1-1. 문제 정의</h2>
대학생들은 교내 수업 뿐만이 아니라, 다양한 동아리나 스터디, 대외 활동을 통해 새로운 가치를 창출하고 자신의 가치를 쌓아나갑니다. 
<br/><br/>

그러나 이 과정에서 대학생들은 아래와 같은 문제 상황을 겪게 됩니다.

1) 모임 활동 중 각 팀원들의 가용 시간을 찾아 활동 시간을 정하는 과정이 힘듭니다.
2) 다양한 활동 중에 생겨나는 무수한 할 일들을 관리하는 과정이 복잡하고, 비효율적입니다.
3) 모임별 ToDo나 나의 일정을 관리하는 서비스가 부재합니다.

따라서 저희는 교내 모임별 가용시간을 파악하고, 일정 관리를 하는 모임 투데이로 해당 문제점들을 해결하고자합니다.

<h2>1-2. 문제 해결을 위한 주요 기능</h2>
<h3> 1. 에브리타임 시간표 기반 가용 시간 제공</h3>
대부분의 대학생들은 시간표 관리를 위해 에브리타임을 이용합니다. 저희 모임투데이는 대학생들의 사용 편의를 위해 기존에 사용하고 있던 에브리타임의 시간표 url 값을 입력받아 가용 시간을 제공합니다.
<h3> 2. 개인 일정 추가 기능 및 모임별 ToDo 제공</h3>
대학생들은 교내 수업 외 다양한 모임(스터디, 동아리, 대외활동 등)에 참여하고, 해당 모임에서 다양한 일정들과 할 일들이 생겨납니다. 저희 모임 투데이에서는 기존 대학생들의 일정 관리에 대한 본질을 생각해 문제를 해결하고자 개인 일정을 추가하거나 모임별 ToDo를 추가하는 기능을 제공합니다.
<h3> 3. 모임 내 참여자 가용 시간 제공</h3>
다양한 형태의 모임에서, 언제나 힘이 드는 것은 바로 모임 내 참여자들의 가용 시간을 파악해 팀 활동 시간을 정하는 일입니다. 저희 모임 투데이 에서는 참여자 개인의 일정, 시간표 정보, 모임 일정들을 종합하여 가용 시간을 제공합니다.

<h1>2. 모임 투데이를 만든 기술</h1>
<h3>FE</h3>
<div>
  <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
 <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
<img src="https://img.shields.io/badge/tailwindcss-a5f3fc?style=for-the-badge&logo=tailwindcss&logoColor=black"/> 

</div>
<h3>BE</h3>
<div>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/h2-003545?style=for-the-badge&logo=h2&logoColor=white">
  <img src="https://img.shields.io/badge/spring%20data%20jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/querydsl-00B1B3?style=for-the-badge&logo=querydsl&logoColor=white">
</div>
<div>
  <h3>DevOps</h3>
    <img src="https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white">
    <img src="https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white">
    <img src="https://img.shields.io/badge/AWS%20S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white">
    <img src="https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white">
</div>

<div>
  <h3>협업</h3>
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white">
  <img src="https://img.shields.io/badge/Slack-FFD700?style=for-the-badge&logo=slack&logoColor=white">
</div>
<h1 id="team">  3. 팀 정보 (Team Information)</h2>

<table width="1200">
  <thead>
    <tr>
      <th width="100" align="center">Profile</th>
      <th width="100" align="center">Name</th>
      <th width="250" align="center">Role</th>
      <th width="200" align="center">E-mail</th>
      <th width="200" align="center">Github</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td width="100" align="center">
        <img src="https://avatars.githubusercontent.com/u/84896838?s=400&u=d6993beb7b2e018232eb13e424cb5f7f3fc6c704&v=4"
          width="100%" height="100%">
      </td>
      <td width="100" align="center">정유환</td>
      <td width="200" align="center">TEAM LEADER<br>Backend Developer</td>
      <td width="200" align="center">yhwjd99@ajou.ac.kr</td>
      <td width="200" align="center">
        <a href="https://github.com/320Hwany" target="_blank">
          <img src="https://img.shields.io/badge/github-181717.svg?style=for-the-badge&logo=github&logoColor=white"
            alt="github" />
        </a>
      </td>
    </tr>
    <tr>
      <td width="100" align="center">
        <img src="https://avatars.githubusercontent.com/u/83941092?v=4"
          width="100%" height="100%">
      </td>
      <td width="100" align="center">김동현</td>
      <td width="200" align="center">Spring<br>Backend Developer</td>
      <td width="200" align="center">ilovekdh1208@ajou.ac.kr</td>
      <td width="200" align="center">
        <a href="https://github.com/Anak-2" target="_blank">
          <img src="https://img.shields.io/badge/github-181717.svg?style=for-the-badge&logo=github&logoColor=white"
            alt="github" />
        </a>
      </td>
    </tr>
    <tr>
      <td width="100" align="center">
        <img src="https://avatars.githubusercontent.com/u/109820506?s=400&u=fc8afe76399865166d588f3f35ac0b59aff30808&v=4"
          width="100%" height="100%">
      </td>
      <td width="100" align="center">양지웅</td>
      <td width="200" align="center">Spring<br>Backend Developer</td>
      <td width="200" align="center">jerry3269@ajou.ac.kr</td>
      <td width="200" align="center">
        <a href="https://github.com/jerry3269" target="_blank">
          <img src="https://img.shields.io/badge/github-181717.svg?style=for-the-badge&logo=github&logoColor=white"
            alt="github" />
        </a>
      </td>
    </tr>
    <tr>
      <td width="100" align="center">
        <img src="https://github.com/U2DJ/booki-today/assets/76727686/09460a5f-8fc7-4fdf-ade3-4ff0eb5a82c9"
          width="100%" height="100%">
      </td>
      <td width="100" align="center">김유림</td>
      <td width="200" align="center">React<br>Frontend Developer</td>
      <td width="200" align="center">yurim@ajou.ac.kr</td>
      <td width="200" align="center">
        <a href="https://github.com/Yurim222" target="_blank">
          <img src="https://img.shields.io/badge/github-181717.svg?style=for-the-badge&logo=github&logoColor=white"
            alt="github" />
        </a>
      </td>
    </tr>
    <tr>
      <td width="100" align="center">
        <img src="https://avatars.githubusercontent.com/u/36218321?v=4.png"
          width="100%" height="100%">
      </td>
      <td width="100" align="center">김준영</td>
      <td width="200" align="center">React<br>Frontend Developer</td>
      <td width="200" align="center">tjclear214@ajou.ac.kr</td>
      <td width="200" align="center">
        <a href="https://github.com/linearjun" target="_blank">
          <img src="https://img.shields.io/badge/github-181717.svg?style=for-the-badge&logo=github&logoColor=white"
            alt="github" />
        </a>
      </td>
    </tr>
  </tbody>
</table>

<h1>4. 문제 상황과 해결</h1>
<ul>
  
<a href="https://velog.io/@anak_2/%EA%B9%83-%ED%98%91%EC%97%85%EC%97%90%EC%84%9C-%EC%9D%B4%EC%8A%88Issue%EC%99%80-%ED%92%80%EB%A6%AC%ED%80%98%EC%8A%A4%ED%8A%B8PR%EC%9D%84-%ED%85%9C%ED%94%8C%EB%A6%BF-template%EC%9C%BC%EB%A1%9C-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0">깃 협업에서 이슈(Issue)와 풀리퀘스트(PR)을 템플릿 (template)으로 관리하기</a> 
  
<a href="https://320hwany.tistory.com/112">팀 프로젝트를 위한 github actions CI/CD 세팅</a>
  
<a href="https://south-leopard-b1c.notion.site/58936ac8371a4e49a333a698d99bb0ce">백엔드 모니터링 환경을 구축해보자!</a>

<a href="https://320hwany.tistory.com/113">RDB는 정말 유연한 설계에 대응하는 것이 어려울까?</a>

<a href="https://320hwany.tistory.com/114">대량의 insert 쿼리 jdbc batch update를 사용하여 개선하기</a>

<a href="https://320hwany.tistory.com/116">복잡한 비즈니스 로직을 풀어내는 방법</a>

<a href="https://south-leopard-b1c.notion.site/881b7ad598f04843a8970201ea9cad53">쿠키-세션을 이용한 로그인 방식 선택과 이유</a>

<a href="https://320hwany.tistory.com/115">저희 팀에서는 이렇게 테스트 코드를 작성해요</a>

<a href="https://south-leopard-b1c.notion.site/8422e7fa404543f48dd0331cc0578297">독립 테스트는 성공하는데 동시 테스트는 실패하는 이유 찾기</a>
</ul>
