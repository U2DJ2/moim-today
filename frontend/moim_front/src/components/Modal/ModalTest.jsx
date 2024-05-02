import "./ModalTest.css";

export default function Modal({ isOpen, closeModal, message }) {
  // Add class to body when modal is active to remove scroll bar
  if (isOpen) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  return (
    <>
      {isOpen && (
        <div className="modal">
          <div onClick={closeModal} className="overlay"></div>
          <div className="modal-content">
            <h2>Hello Modal</h2>
            <p>{message}</p>
            <button className="close-modal" onClick={closeModal}>
              CLOSE
            </button>
          </div>
        </div>
      )}
    </>
  );
}
