// React
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

// Material UI
import { Slide } from "@material-ui/core";
import MuiAlert from "@material-ui/lab/Alert";

// Icons
import EditIcon from '@mui/icons-material/Edit';
import HomeIcon from '@mui/icons-material/Home';
import ArticleIcon from '@mui/icons-material/Article';
import PersonIcon from '@mui/icons-material/Person';


// CSS
import "./Manage.css";

function Alert(props) {
    return <MuiAlert elevation={6}
        variant="filled" {...props} />;
}

function ManageIcon() {
    return (
        <div className="flex flex-col justify-center items-start p-5 bg-rose-600 rounded-3xl max-md:pr-5">
            <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/a62114f657cd2fa44c2617cafc5a564692ee84397f85414112c4d73292a6479e?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&" alt="" className="w-7 aspect-square" />
        </div>
    );
}

function SidebarLink({ icon, text, color }) {
    return (
        <div className={`flex items-center gap-2.5 py-4 text-base font-semibold ${color}`}>
            {icon}
            <div className="flex-1 flex items-center justify-between">{text}</div>
        </div>
    );
}


function Sidebar() {
    const navigate = useNavigate();

    // 홈 버튼 클릭 시 메인 페이지로 이동
    const handleHome = () => {
        navigate('/');
    };

    return (
        <aside className="flex flex-col w-[20%] max-md:ml-0 max-md:w-full">
            <div className="flex flex-col grow items-start self-stretch py-7 pr-3 pl-12 max-md:pl-5 max-md:mt-6">
                <ManageIcon />
                <div className="mt-3 text-base font-semibold text-center text-zinc-500">
                    모임 대시보드{" "}
                </div>
                <div className="flex gap-2.5 mt-5"></div>
                <a href="" onClick={handleHome}><SidebarLink icon={<HomeIcon />} text="홈" color="text-gray-400" className="mt-16 max-md:mt-10" /></a>
                <SidebarLink icon={<ArticleIcon />} text="모임 관리" color="text-gray-400" className="mt-16 max-md:mt-10" />
                <SidebarLink icon={<PersonIcon />} text="프로필 설정" color="text-red-600" className="mt-2.5" />
            </div>
        </aside>
    );
}


function InputField({ label, value }) {
    const [inputValue, setInputValue] = useState(value);
    const [open, setOpen] = useState(false);

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleInputBlur = () => {
        setOpen(true);
        // console.log("수정된 값:", inputValue);
    };

    useEffect(() => {
        let timer;
        if (open) {
            timer = setTimeout(() => {
                setOpen(false);
            }, 2000);
        }
        return () => {
            clearTimeout(timer);
        };
    }, [open]);

    return (
        <>
            <div className="mt-5 max-md:max-w-full">{label}</div>
            <div className="flex gap-2.5 px-4 py-3.5 mt-2 leading-5 rounded-3xl border border-solid border-zinc-300 text-zinc-800 max-md:flex-wrap max-md:pr-5">
                <input
                    type="text"
                    value={inputValue}
                    onChange={handleInputChange}
                    onBlur={handleInputBlur}
                    className="w-full bg-transparent outline-none text-gray-600"
                />
                <EditIcon />
            </div>
            <Slide direction="up" in={open} mountOnEnter unmountOnExit>
                <div style={{ position: "fixed", bottom: 20, right: 20 }}>
                    <Alert severity="success" onClose={() => setOpen(false)}>
                        수정된 값: {inputValue}
                    </Alert>
                </div>
            </Slide>
        </>
    );
}

function ProfileSection() {
    return (
        <section className="flex flex-col ml-5 w-[80%] max-md:ml-0 max-md:w-full h-full">
            <div className="flex flex-col self-stretch p-12 w-full text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_64px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-full flex-grow">
                <h1 className="text-6xl text-black max-md:max-w-full max-md:text-4xl">Profile</h1>
                <InputField label="이름" value="김준영" />
                <InputField label="학과" value="소프트웨어학과" />
            </div>
        </section>
    );
}

export default function Manage() {
    return (
        <div className="justify-between pt-9 bg-white h-screen flex flex-col"> {/* flex 속성 추가 */}
            <div className="flex-1 overflow-auto"> {/* flex-1 및 overflow-auto 속성 추가 */}
                <div className="flex gap-5 h-full max-md:flex-col max-md:gap-0">
                    <Sidebar />
                    <ProfileSection />
                </div>
            </div>
        </div>
    );
}
