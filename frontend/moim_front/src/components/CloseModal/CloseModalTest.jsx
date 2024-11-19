import "./CloseModalTest.css";

export default function Modal({ isOpen, setIsOpen, message }) {
  // Add class to body when modal is active to remove scroll bar
  if (isOpen) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  const closeModal = () => {
    setIsOpen(false);
  };

  return (
    <>
      {isOpen && (
        <div className="modal">
          <div onClick={closeModal} className="overlay"></div>
          <div className="modal-content">
            <div className="flex flex-col gap-5 w-96 h-28">
              <h2 className="font-Praise text-scarlet">Moim-Today</h2>
              <p className="font-Pretendard_Black text-xl">{message}</p>
              <button
                className="close-modal font-Pretendard_Light"
                onClick={closeModal}
              >
                CLOSE
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
