// React
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import axios from "axios";
import { Accordion, Checkbox, Label } from "flowbite-react";

function ToDo() {
  const [todoData, setTodoData] = useState([]);
  let { MoimId } = useParams();

  useEffect(() => {
    fetchTodoData();

    // eslint-disable-next-line
  }, []);

  const fetchTodoData = async () => {
    var startDate = new Date();

    // Parse start date
    startDate = new Date(startDate);
    startDate = startDate.toISOString().slice(0, 7);

    try {
      const response = await axios.get(
        `https://api.moim.today/api/todos/moim/${MoimId}?startDate=${startDate}&months=12`
      );
      setTodoData(response.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleAddTodo = () => {
    // 추가할 일이 있다면 여기에 구현
  };

  const handleTodoCheckboxClick = (todo) => {
    // 체크박스 클릭 시 처리할 로직
    console.log(todo);
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
  };

  return (
    <div>
      <div className="grid grid-cols-4 gap-4">
        {todoData.map((item) => (
          <button
            key={item.memberId} // memberId를 key로 사용
            className="w-auto justify-center px-10 py-2 text-[16px] text-center text-white bg-black whitespace-nowrap rounded-full font-semibold  hover:cursor-pointer"
            onClick={handleAddTodo}
          >
            {item.memberId}
          </button>
        ))}
      </div>
      <div className="mt-4"></div>
      <div className=" w-full">
        {Object.entries(groupTodosByDate()).map(([date, todos]) => (
          <Accordion key={date} className="w-full">
            <Accordion.Panel>
              <Accordion.Title className="font-Pretendard_SemiBold text-[16px]">
                {date}
              </Accordion.Title>
              <Accordion.Content>
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
                  </div>
                ))}
              </Accordion.Content>
            </Accordion.Panel>
          </Accordion>
        ))}
      </div>
    </div>
  );
}

export default ToDo;
