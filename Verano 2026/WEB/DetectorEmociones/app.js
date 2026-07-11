const video = document.getElementById("video");
const emoji = document.getElementById("emoji");
const emotionText = document.getElementById("emotion-text");


async function loadModels() {
    await faceapi.nets.tinyFaceDetector.loadFromUri("./models")
    await faceapi.nets.faceExpressionNet.loadFromUri("./models")
    console.log("Los modelos se han cargado correctamente");



}

function getMainEmotion(expressions){
    let maxValue = 0;
    let mainEmotion = "neutral";

    for (let emotion in expressions){
        if(expressions[emotion] > maxValue){
            maxValue = expressions[emotion];
            mainEmotion = emotion;
        }
    }
    return mainEmotion;



}





async function startCamara() {
    const stream = await navigator.mediaDevices.getUserMedia({
        video:true
    })
    video.srcObject= stream;
}

async function detectEmotion() {
    const detection = await faceapi.detectSingleFace(
        video, 
        new faceapi.TinyFaceDetectorOptions()
    ).withFaceExpressions();

    if(detection){
        const expressions = detection.expressions;
        console.log(expressions)


        const emotion =getMainEmotion(expressions)

        updateUI(emotion);

    }
}


function updateUI(emotion){
    const emojis = {
        happy: "😄",
        sad: "😢",
        angry: "😡",
        surprised: "😲",
        fearful: "😨",
        disgusted: "🤢",
        neutral: "😐"
    };

  
    const texts = {
        happy: "Felicidad",
        sad: "Tristeza",
        angry: "Enojo",
        surprised: "Sorpresa",
        fearful: "Miedo",
        disgusted: "Desagrado",
        neutral: "Neutral"
    };

    emoji.textContent = emojis[emotion]
    emotionText.textContent = `Emocion detectada: ${texts[emotion]}}`;

}


async function init() {

    await loadModels();
    await startCamara();
    
    setInterval(detectEmotion, 200);



}

init();

