# gitlab

- 获取项目列表：GET /projects
- 获取项目信息：GET /projects/:id
- 创建项目：POST /projects
- 更新项目信息：PUT /projects/:id
- 删除项目：DELETE /projects/:id
- 获取项目成员列表：GET /projects/:id/members
- 添加项目成员：POST /projects/:id/members
- 更新项目成员信息：PUT /projects/:id/members/:id
- 删除项目成员：DELETE /projects/:id/members/:id
- 获取项目分支列表：GET /projects/:id/repository/branches
- 获取分支信息：GET /projects/:id/repository/branches/:branch
- 创建分支：POST /projects/:id/repository/branches
- 删除分支：DELETE /projects/:id/repository/branches/:branch
- 获取项目标签列表：GET /projects/:id/repository/tags
- 获取标签信息：GET /projects/:id/repository/tags/:name
- 创建标签：POST /projects/:id/repository/tags
- 删除标签：DELETE /projects/:id/repository/tags/:name
- 获取文件树形结构：GET /projects/:id/repository/tree
- 获取单个文件内容：GET /projects/:id/repository/files/:file_path
- 更新文件内容：PUT /projects/:id/repository/files/:file_path
- 删除文件：DELETE /projects/:id/repository/files/:file_path
- 获取 Merge Request 列表：GET /projects/:id/merge_requests
- 创建 Merge Request：POST /projects/:id/merge_requests
- 更新 Merge Request：PUT /projects/:id/merge_requests/:merge_request_iid
- 删除 Merge Request：DELETE /projects/:id/merge_requests/:merge_request_iid
- 获取 Merge Request 的评论列表：GET /projects/:id/merge_requests/:merge_request_iid/notes
- 添加 Merge Request 的评论：POST /projects/:id/merge_requests/:merge_request_iid/notes
- 获取最近的 Commit 列表：GET /projects/:id/repository/commits
- 获取单个 Commit 信息：GET /projects/:id/repository/commits/:sha