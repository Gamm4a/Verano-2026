
const API_URL = 'http://localhost:8080/TasksAPI/api/task'
export let tasks = []
export const fetchTasks = async () => {
    const response = await fetch(API_URL);
    tasks = await response.json()
    return tasks
}

export const addTask =  async (text) => {
    const response = await fetch(API_URL, {
        method: "POST",
        headers: {"Content-Type": "application/json" },
        body: JSON.stringify({text: text, completed: false})
    }) 
    
    const newTask = await response.json();
    tasks.push(newTask)
    return tasks
}

export const deleteTask = async (id) => {
    const response = await fetch(`${API_URL}?id=${id}`, {
        method: "DELETE",
    }) 

    tasks = tasks.filter(t => t.id !== id);
    return tasks;
};

export const toggleTask = async (id) => {
    const task = tasks.find(t => t.id === id);
    if (!task) return;

    // Preparamos el objeto con el estado invertido
    const updatedTask = { ...task, completed: !task.completed };
    
    const response = await fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedTask)
    });
    
    const savedTask = await response.json();
    task.completed = savedTask.completed; // Sincronizamos
    return tasks;
};

// PUT: Editar texto
export const editTask = async (id, newText) => {
    const task = tasks.find(t => t.id === id);
    if (!task) return;

    const updatedTask = { ...task, text: newText };

    const response = await fetch(API_URL, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedTask)
    });

    const savedTask = await response.json();
    task.text = savedTask.text; // Sincronizamos
    return tasks;
};