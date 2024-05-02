// React
import { useState, Fragment } from "react";
import { Transition } from "@headlessui/react";

function Search({ options, onSelect, setActiveNext }) {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedOption, setSelectedOption] = useState(null);

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const handleOptionSelect = (option, index) => {
    onSelect(index);
    setSelectedOption(option);
    toggleDropdown();
    setActiveNext(true);
  };

  const handleInputChange = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  return (
    <div className="relative inline-block text-left">
      <div>
        <button
          id="dropdown-button"
          className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-4 py-3 text-sm font-semibold text-gray-700 shadow-sm ring-1 ring-gray-200 hover:bg-gray-50"
          onClick={toggleDropdown}
        >
          {selectedOption === null ? "학과를 선택해주세요" : selectedOption}
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="-mr-1 h-5 w-5 text-gray-400"
            viewBox="0 0 20 20"
            fill="currentColor"
            aria-hidden="true"
          >
            <path
              fillRule="evenodd"
              d="M6.293 9.293a1 1 0 011.414 0L10 11.586l2.293-2.293a1 1 0 111.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z"
              clipRule="evenodd"
            />
          </svg>
        </button>
      </div>

      <Transition
        show={isOpen}
        as={Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95"
      >
        <div className="absolute left-0 px-1 py-1 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-sm ring-1 ring-black ring-opacity-5 overflow-y-auto max-h-60 focus:outline-none">
          <input
            id="search-input"
            className="block w-full px-4 py-2 text-gray-800 border rounded-md border-gray-200 focus:outline-none"
            type="text"
            placeholder="학과명을 검색해주세요"
            autoComplete="off"
            onChange={handleInputChange}
          />
          {options.map((option, index) => (
            <a
              key={index}
              href="#"
              className="block px-4 py-2 text-sm"
              onClick={() => handleOptionSelect(option, index)}
              style={{
                display: option.toLowerCase().includes(searchTerm)
                  ? "block"
                  : "none",
              }}
            >
              {option}
            </a>
          ))}
        </div>
      </Transition>
    </div>
  );
}

export default Search;
