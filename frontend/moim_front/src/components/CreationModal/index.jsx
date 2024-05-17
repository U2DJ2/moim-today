import React, { useRef } from "react";

function CreationModal({ showModal, setShowModal, children, noticeHandler }) {
  const modalRef = useRef();
  const closeModal = () => {
    noticeHandler();
    setShowModal(false);
  };

  const modalOutSideClick = (e) => {
    if (modalRef.current === e.target) closeModal();
  };

  return (
    <div
      className={`modal-container fixed top-0 left-0 z-50 w-screen h-screen bg-slate-400 bg-opacity-50 scale-100 flex ${
        showModal ? "active" : ""
      }`}
      ref={modalRef}
      onClick={(e) => modalOutSideClick(e)}
    >
      <div className="m-auto shadow w-96 rounded-xl">
        <div className="flex flex-col justify-center h-64 text-center text-black whitespace-pre bg-white rounded-t-xl">
          {children}
        </div>
        <button
          className="w-full h-16 text-lg text-center text-white rounded-b-xl bg-scarlet font-Pretendard_Light"
          onClick={closeModal}
        >
          확인
        </button>
      </div>
    </div>
  );
}

export default CreationModal;
