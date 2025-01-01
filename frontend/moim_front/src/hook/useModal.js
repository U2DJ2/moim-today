import { useState, useCallback } from "react";

export default function useModal () {
    const [showModal, setShowModal] = useState(false);
    const [message, setMessage] = useState("");

    const openModal = useCallback((newMessage) => {
        setMessage(newMessage);
        setShowModal(true)
    },[]);

    const closeModal = useCallback(()=>{
        setShowModal(false);
        setMessage("");
    },[])

    return {showModal, setShowModal, message, openModal, closeModal}
}
