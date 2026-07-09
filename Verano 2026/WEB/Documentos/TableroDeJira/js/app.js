import * as UI from "./ui.js";
import * as state from "./state.js";


export let tasks = [];

export const fetchTasks = async () => {
        const response = await fetch(API_URL);
        tasks = await response.json();
        return tasks;
}

document.addEventListener("DOMContentLoaded", async () => {
    const input = document.getElementById("taskInput");
const addBtn= document.querySelector(".btn-add");
const list= document.getElementById("taskList");

    const tasks= await state.fetchTasks();
    UI.render(tasks);


input.addEventListener("keydown", (e)=>{
    if(e.key === "Enter"){
        addTask();
    }
})

    addBtn.addEventListener("click", addTask);


    
    async function addTask(){
    if (input.value.trim() === '') return;

    state.addTask(input.value);

    let tasks = await state.fetchTasks();

    UI.render(tasks);
    }


list.addEventListener('click', (e) => {
    const id = parseInt(e.target.closest('li').dataset.id);

    if (e.target.classList.contains("btn-delete")) {
        state.deleteTask(id);
    } else if (e.target.classList.contains("btn-edit")) {
        const task = state.tasks.find(t => t.id === id);

        const newTask = prompt("Editar tarea:", task.text)

        if (newTask) {
            state.editTask(id, newTask);
        }
    } else if (e.target.tagName !== 'BUTTON'){
        state.toggleTask(id);
    }
    UI.render(state.tasks);
})
});
