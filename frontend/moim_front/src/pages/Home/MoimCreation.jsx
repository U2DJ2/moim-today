import { useNavigate } from 'react-router-dom';

function CategorySelector() {
    return (
        <div className="flex gap-0 justify-between px-4 py-2.5 mt-2 text-sm font-semibold leading-7 rounded-xl bg-neutral-50 text-zinc-400 max-md:flex-wrap">
            <div className="flex-1 max-md:max-w-full">어디까지 올라가는거에요??</div>
            <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/95da3276034397a5966cf1c2c5fefc4519307760c7e98daff957792b9dc250d0?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&" className="shrink-0 my-auto w-6 aspect-square" />
        </div>
    );
}

function InputField({ label, placeholder }) {
    return (
        <>
            <div className="mt-2.5 text-sm font-semibold leading-5 text-stone-500 max-md:max-w-full">
                {label}
            </div>
            <div className="justify-center px-4 py-2.5 mt-2 text-sm font-semibold leading-7 rounded-xl bg-neutral-50 text-zinc-400 max-md:max-w-full">
                <input type="text" className="w-full bg-transparent outline-none text-gray-600" placeholder={placeholder} />
            </div>
        </>
    );
}

function ImageUploader() {
    return (
        <div className="flex justify-center items-center px-4 py-20 mt-2 rounded-xl border border-dashed border-neutral-400 max-md:px-5 max-md:max-w-full">
            <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/6bf0157b2a958fbe906dbd51a7e42975c058c0ecf274028bbcdbf686001a61ed?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&" className="my-3 w-14 aspect-square fill-zinc-300" />
        </div>
    );
}

export default function MoimCreation() {

    const navigate = useNavigate();

    // "취소하기" 버튼 클릭 시 메인 페이지로 이동
    const handleCancel = () => {
        navigate('/');
    };

    return (
        <div className="flex flex-col justify-center p-8 bg-white rounded-[32px] max-md:px-5">
            <header className="flex gap-0 justify-between font-semibold text-black leading-[150%] max-md:flex-wrap">
                <h1 className="flex-1 text-3xl max-md:max-w-full">모임을 생성해주세요</h1>
                <div className="flex gap-2 justify-center py-3 text-lg whitespace-nowrap">
                    <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/a7bb680ab431d8653bc4b53b87bc26a6b767d05f0b1c6a5303549035c8fcc8b7?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&" className="shrink-0 my-auto w-6 aspect-square" />
                    <div>공개</div>
                </div>
            </header>
            <main>
                <div className="mt-10 text-sm font-semibold leading-5 text-stone-500 max-md:max-w-full"> 카테고리 </div>
                <CategorySelector />
                <InputField label="모임명" placeholder="모임 이름을 적어주세요." />
                <InputField label="상세 설명" placeholder="모임 설명을 적어주세요." />
                <div className="mt-2.5 text-sm font-semibold leading-5 text-stone-500 max-md:max-w-full"> 이미지 올리세요 </div>
                <ImageUploader />
                <InputField label="운영 시간" placeholder="이거 클릭하면 Date Picker 떠야 됨" />
                <InputField label="참여 인원" placeholder="참여 인원을 숫자만 입력해주세요!" />
            </main>
            <footer className="flex justify-center gap-12">
                <button className="mt-10 text-lg font-semibold leading-7 text-gray-400" onClick={handleCancel}> 취소하기 </button>
                <button className="mt-10 text-lg font-semibold leading-7 text-rose-600"> 생성하기 </button>
            </footer>
        </div>
    );
}