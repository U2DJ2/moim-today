// React
import React from "react";
import { useNavigate } from "react-router";

// Headless UI
import { Menu, Transition } from "@headlessui/react";

// API
import axios from "axios";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const Profile = ({ image }) => {
  const navigate = useNavigate();

  const handleProfile = () => {
    navigate("/manage", {
      state: {
        componentName: "profile",
      },
    });
  };

  const handleLogout = async () => {
    try {
      await axios.post("https://api.moim.today/api/logout");
    } catch (error) {
      console.error("Failed to validate session", error);
      navigate("/login");
    }
  };

  return (
    <Menu as="div" className="relative inline-block text-left">
      <div>
        <Menu.Button className="inline-flex items-center gap-x-1.5 rounded-md bg-white px-3 py-2">
          <img
            src={image}
            alt="Profile"
            className="shrink-0 w-10 h-10 rounded-full"
          />
        </Menu.Button>
      </div>

      <Transition
        as={React.Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95"
      >
        <Menu.Items className="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-md ring-1 ring-black ring-opacity-5 font-Pretendard_Medium focus:outline-none">
          <div className="py-1">
            <Menu.Item>
              {({ active }) => (
                <a
                  onClick={handleProfile}
                  className={classNames(
                    active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                    "block px-4 py-2 text-sm"
                  )}
                >
                  Account settings
                </a>
              )}
            </Menu.Item>
            <form>
              <Menu.Item>
                {({ active }) => (
                  <button
                    type="submit"
                    onClick={handleLogout}
                    className={classNames(
                      active ? "bg-gray-100 text-gray-900" : "text-gray-700",
                      "block w-full px-4 py-2 text-left text-sm"
                    )}
                  >
                    Sign out
                  </button>
                )}
              </Menu.Item>
            </form>
          </div>
        </Menu.Items>
      </Transition>
    </Menu>
  );
};

export default Profile;
