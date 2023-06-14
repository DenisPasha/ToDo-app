import React, { useState } from "react";
import CreateTask from "./CreateTask";
import ReadTasks from "./ReadTasks";
import DeleteTask from "./DeleteTask";
import Update from "./Update";
import ExportTasks from "./ExportTasks";

export default function App() {
  const [createRender, setCreateRender] = useState(false);
  const [readAllRender, setReadAllRender] = useState(false);
  const [deleteRender, setDeleteRender] = useState(false);
  const [updateRender, setUpdateRender] = useState(false);
  const [exported, setExported] = useState(false);

  function handleClickOnCreate() {
    setDeleteRender(false);
    setReadAllRender(false);
    setUpdateRender(false);
    setExported(false);

    return setCreateRender((prev) => !prev);
  }

  function handleClickReadAll() {
    setDeleteRender(false);
    setUpdateRender(false);
    setExported(false);
    setCreateRender(false);

    return setReadAllRender((prev) => !prev);
  }

  function handleClickDelete() {
    setUpdateRender(false);
    setExported(false);
    setCreateRender(false);
    setReadAllRender(false);

    return setDeleteRender((prev) => !prev);
  }

  function handleClickUpdate() {
    setExported(false);
    setCreateRender(false);
    setReadAllRender(false);
    setDeleteRender(false);
    return setUpdateRender((prev) => !prev);
  }

  function exportPDF() {
    setCreateRender(false);
    setReadAllRender(false);
    setDeleteRender(false);
    setUpdateRender(false);

    return setExported((prev) => !prev);
  }

  function handleExport() {
    fetch("http://localhost:8080/api/export-to-pdf").then((res) => {
      if (res.ok) {
        exportPDF();
      }
    });
  }

  return (
    <div className="main">
      <div className="title-holder">
        <h1>Task management</h1>
      </div>

      <div className="mainContentHolder">
        <button onClick={handleClickOnCreate}>Create</button>

        <button onClick={handleClickReadAll}>Read</button>

        <button onClick={handleClickUpdate}>Update</button>

        <button onClick={handleClickDelete}>Delete</button>

        <button onClick={handleExport}>Export</button>
      </div>

      <div className="content">
        {createRender && <CreateTask />}
        {readAllRender && <ReadTasks />}
        {deleteRender && <DeleteTask />}
        {updateRender && <Update />}
        {exported && <ExportTasks />}
      </div>
    </div>
  );
}
