import { HiOutlineExclamationCircle } from "react-icons/hi";
import { Modal } from "flowbite-react";
import ModalTheme from "../AlertModal/modalTheme.js";

export default function AlertModal({show, onClose, message}) {
    return(
        <>
            <Modal show={show} size="lg" onClose={onClose} theme={ModalTheme} popup>
                <Modal.Header />
                <Modal.Body>
                    <div className="text-center">
                        <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
                        <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
                            {message}
                        </h3>
                        <div className="flex justify-center gap-4">
                            <button
                                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                                onClick={onClose}
                            >
                                다시 입력하기
                            </button>
                        </div>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

