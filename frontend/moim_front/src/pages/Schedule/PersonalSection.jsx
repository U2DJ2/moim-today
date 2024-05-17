// Components
import Calendar from "../../components/Calendar/PersonalCalendar";

export default function ProfileSection({ selectedDate }) {
    return (
        <section className="flex flex-col w-full max-md:ml-0 max-md:w-full">
            <div className="flex flex-col self-stretch p-12 text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_0px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-fit flex-grow">
                <h1 className="text-6xl text-black max-md:max-w-full max-md:text-4xl">Weekly</h1>
                <div className="py-3"></div>
                <Calendar selectedDate={selectedDate} />
            </div>
        </section>
    );
}