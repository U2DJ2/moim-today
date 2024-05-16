import loadingSpinner from "../../assets/images/loading.gif";

function Loading({ full }) {
  return (
    <>
      <div
        className={`w-full flex justify-center items-center ${
          full ? "fixed top-0 left-0 h-real-screen" : "my-10"
        }`}
      >
        <img src={loadingSpinner} alt="loading..." className="w-24 h-24" />
      </div>
      <div className={`${full ? "h-real-screen" : "hidden"}`} />
    </>
  );
}

export default Loading;
