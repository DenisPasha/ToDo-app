import React from "react";
const exportPath = "src/main/java/com/example/taskmanagementapp/output/file.pdf";


export default function ExportTasks(){

    return(
        <div className="export">
            <h4>Exported all tasks to: {exportPath} </h4>
        </div>
        
    )
}