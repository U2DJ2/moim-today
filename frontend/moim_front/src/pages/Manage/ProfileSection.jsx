// React
import { useState, useEffect } from "react";

// Material UI
import { Slide } from "@material-ui/core";
import MuiAlert from "@material-ui/lab/Alert";

// Icons
import EditIcon from '@mui/icons-material/Edit';


function Alert(props) {
    return <MuiAlert elevation={6}
        variant="filled" {...props} />;
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

export default function ProfileSection() {
    return (
        <section className="flex flex-col w-full max-md:ml-0 max-md:w-full h-full">
            <div className="flex flex-col self-stretch p-12 text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_64px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-full flex-grow">
                <h1 className="text-6xl text-black max-md:max-w-full max-md:text-4xl">Profile</h1>
                <InputField label="이름" value="김준영" />
                <InputField label="학과" value="소프트웨어학과" />
            </div>
        </section>
    );
}