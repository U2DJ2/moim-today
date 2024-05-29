import { useEffect, useState } from "react";
import { useParams } from "react-router";
import axios from "axios";
import { Accordion, Checkbox, Label } from "flowbite-react";

function ToDo() {
  const [todoData, setTodoData] = useState([]);
  const [memberData, setMemberData] = useState([]);
  const [selectedMember, setSelectedMember] = useState(null);

  let { MoimId } = useParams();

  useEffect(() => {
    fetchData();

    // eslint-disable-next-line
  }, [selectedMember]); // selectedMember 상태 변경 감지

  const fetchData = async () => {
    try {
<<<<<<< HEAD
      const response = await axios.get(
        `https://api.moim.today/api/todos/moim/${MoimId}?startDate=${startDate}&months=12`
      );
      setTodoData(response.data.data);
=======
      const response = await axios.get(`https://api.moim.today/api/moims/members/${MoimId}`);
      setMemberData(response.data.moimMembers);
      
      if (selectedMember === null && response.data.moimMembers.length > 0) {
        // 선택된 멤버가 없는 경우, 첫 번째 멤버를 선택하도록 설정
        setSelectedMember(response.data.moimMembers[0].memberId);
      }

      if (selectedMember !== null) {
        let requestDate = new Date(new Date().getFullYear(), 0, 1);
        requestDate = requestDate.toISOString().slice(0, 7);

        const todoResponse = await axios.get(`https://api.moim.today/api/todos/moim/${MoimId}?requestDate=${requestDate}&months=12&memberId=${selectedMember}`);
        setTodoData(todoResponse.data.data[0].todoGroupByDates);
      }

>>>>>>> frontend-main
    } catch (error) {
      console.log(error);
    }
  };

  const handleMemeberSelet = (selectedMemberId) => {
    setSelectedMember(selectedMemberId);
  };

  const handleTodoCheckboxClick = (todo) => {
    // 체크박스 클릭 시 처리할 로직
    console.log(todo);
<<<<<<< HEAD
  };

  // todo 데이터를 날짜별로 그룹화하는 함수
  const groupTodosByDate = () => {
    const groupedTodos = {};
    todoData.forEach((item) => {
      const todoDate = item.todoResponses.todoDate;
      if (!groupedTodos[todoDate]) {
        groupedTodos[todoDate] = [];
      }
      groupedTodos[todoDate].push(item.todoResponses);
    });
    return groupedTodos;
=======
>>>>>>> frontend-main
  };

  return (
    <>
      <div className="grid grid-cols-4 gap-4">
        {memberData && memberData.map((member) => (
          <button
            key={member.memberId}
            className={`w-auto justify-center px-6 py-3 text-[16px] text-center text-white bg-black whitespace-nowrap rounded-full font-semibold  hover:cursor-pointer ${selectedMember === member.memberId ? 'bg-scarlet' : ''}`}
            onClick={() => handleMemeberSelet(member.memberId)}
          >
            {member.memberName}
          </button>
        ))}
      </div>
      <div>
        {todoData.map((item) => (
          <Accordion key={item.todoDate} className="w-full">
            <Accordion.Panel>
              <Accordion.Title className="font-Pretendard_SemiBold text-[16px]">
                {item.todoDate}
              </Accordion.Title>
              <Accordion.Content>
<<<<<<< HEAD
                {todos.map((todoGroup, groupIndex) => (
                  <div key={groupIndex}>
                    {todoGroup.map((todo, todoIndex) => (
                      <div
                        key={todo.todoId}
                        className={`flex items-center gap-2 ${
                          todoIndex === todoGroup.length - 1 ? "" : " mb-4"
                        }`}
                      >
                        <Checkbox
                          onChange={() => handleTodoCheckboxClick(todo)}
                        />
                        <Label className="font-Pretendard_Medium">
                          {todo.contents}
                        </Label>
                      </div>
                    ))}
=======
                {item.todoContents.map((todo, todoIndex) => (
                  <div key={todo.todoId} className={`flex items-center gap-2 ${todoIndex === item.todoContents.length - 1 ? '' : 'mb-4'}`}>
                    <Checkbox onChange={() => handleTodoCheckboxClick(todo)} />
                    <Label className="font-Pretendard_Medium">{todo.contents}</Label>
>>>>>>> frontend-main
                  </div>
                ))}
              </Accordion.Content>
            </Accordion.Panel>
          </Accordion>
        ))}
      </div>
    </>
  );

}

export default ToDo;
