import './ModalTest.css';

export default function Modal({ isOpen, closeModal }) {
    
    // Add class to body when modal is active to remove scroll bar
    if (isOpen) {
        document.body.classList.add('active-modal')
    } else {
        document.body.classList.remove('active-modal')
    }

    return (
        <>
            {isOpen && (
                <div className="modal">
                    <div onClick={closeModal} className="overlay"></div>
                    <div className="modal-content">
                        <h2>Hello Modal</h2>
                        <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident
                            perferendis suscipit officia recusandae, eveniet quaerat assumenda
                            id fugit, dignissimos maxime non natus placeat illo iusto!
                            Sapiente dolorum id maiores dolores? Illum pariatur possimus
                            quaerat ipsum quos molestiae rem aspernatur dicta tenetur. Sunt
                            placeat tempora vitae enim incidunt porro fuga ea.
                        </p>
                        <button className="close-modal" onClick={closeModal}>
                            CLOSE
                        </button>
                    </div>
                </div>
            )}
        </>
    );
}
