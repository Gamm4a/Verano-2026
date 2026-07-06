import  * as state from "./state.js"
        import * as ui from "./ui.js"
        document.addEventListener(`DOMContentLoaded`, async ()=>{
        const input = document.getElementById("taskInput")
        const addBtn = document.querySelector(".btn-add")
        const list = document.getElementById("taskList")
        const tasks = await state.fetchTasks();

        ui.render(tasks)

        input.addEventListener(`keydown`, (e) => {
            if (e.key === `Enter`) addTask()

        })

        addBtn.addEventListener(`click`, addTask)



        async function addTask(){
            if (!input.value.trim()) return;
           await state.addTask(input.value)
           ui.render(tasks)
        }


        

          list.addEventListener('click', async (e) => {
            const id = parseInt(e.target.closest('li').dataset.id);

        
            if (e.target.classList.contains("btn-delete")) {
                await state.deleteTask(id)
            } else if (e.target.classList.contains("btn-edit")) {
                const task = state.tasks.find(t => t.id === id);
                const newTask = prompt("Editar tarea:", task.text)

                if (newTask) await state.editTask(id, newTask)
                
            } else if (e.target.tagName !== 'BUTTON'){
                await state.toggleTask(id)
            }
            ui.render(state.tasks)
        })
        })