function formatDate(dateString) {
  const isoString = dateString.replace(" ", "T");

  const date = new Date(isoString);

  // 날짜 부분 포맷팅
  const optionsDate = { year: "numeric", month: "long", day: "numeric" };
  const formattedDate = new Intl.DateTimeFormat("ko-KR", optionsDate).format(
    date
  );

  // 시간 부분 포맷팅
  const optionsTime = { hour: "numeric", hour12: true };
  const formattedTime = new Intl.DateTimeFormat("ko-KR", optionsTime).format(
    date
  );

  // 날짜와 시간 결합
  return `${formattedDate} ${formattedTime}`;
}
export default formatDate;
