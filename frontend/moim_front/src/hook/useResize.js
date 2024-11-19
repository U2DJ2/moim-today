import {useState, useEffect} from 'react'

/**
 * 화면 크기가 작은지 여부를 반환하는 커스텀 훅
 * @param {number} breakpoint - 기준 화면 크기 (픽셀)
 * @returns {boolean} isSmall - 화면 크기가 작은지 여부
 */

export default function useResize(breakpoint){
    const [isSmall, setIsSmall] = useState(false);

    useEffect(()=>{
        const handleResize = () => {
            setIsSmall(window.innerWidth < breakpoint);
        }

        window.addEventListener("resize", handleResize);
        handleResize();

        return () => {
            window.removeEventListener("resize", handleResize);
        }
    }, [breakpoint]);
    return isSmall;
}
