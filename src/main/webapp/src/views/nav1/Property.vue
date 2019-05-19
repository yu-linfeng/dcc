<template>
    <section>
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-button type="primary" @click="addNode">新增</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="info" @click="handleDel">编辑</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleDel">删除</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <el-table :data="paths" highlight-current-row v-loading="listLoading" @selection-change="selsChange" @row-click="queryProperty" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="70" label="序号">
            </el-table-column>
            <el-table-column prop="key" label="属性名称">
            </el-table-column>
            <el-table-column prop="desc" label="说明">
            </el-table-column>

        </el-table>

        <!--新增界面-->
        <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false" width="20%">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
                <el-form-item label="属性名称" prop="path">
                    <el-input v-model="addForm.path" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="属性类型" prop="path">
                    <el-select v-model="propertyValueType" placeholder="请选择">
                        <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="属性值" prop="path">
                    <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" v-model="addForm.path" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import axios from 'axios';
    import fecha from 'fecha';
    import util from '../../common/js/util'
    //import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser, queryPath } from '../../api/api';

    export default {
        data() {
            return {
                options: [
                    {
                        value: 'json',
                        label: 'json'
                    }
                ],
                propertyValueType: 'json',
                filters: {
                    path: ''
                },
                users: [],
                paths: [],
                total: 0,
                page: 1,
                listLoading: false,
                sels: [],//列表选中列

                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    path: [
                        { required: true, message: '请输入属性名称', trigger: 'blur' }
                    ]
                },
                //编辑界面数据
                editForm: {
                    id: 0,
                    path: '',
                },

                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    path: [
                        { required: true, message: '请输入名称', trigger: 'blur' }
                    ]
                },
                //新增界面数据
                addForm: {
                    path: '',
                }

            }
        },
        methods: {
            handleCurrentChange(val) {
                this.page = val;
                this.getNodes();
            },
            //获取用户列表
            getNodes() {
                /*this.listLoading = true;
                axios.post(`http://localhost:8080/node/queryNode`, {

                }).then((response) => {
                    this.paths = response.data.data;
                    this.listLoading = false;
                });*/
            },
            //删除
            handleDel: function () {
                if (this.sels.length !== 1) {
                    this.$message({
                        message: '删除失败，最多只能删除一个Path',
                        type: 'error'
                    });
                    return ;
                }
                var node = this.sels[0];

                this.$confirm('确认删除该记录吗?', '提示', {
                }).then(() => {

                    this.listLoading = true;

                    axios.post(`http://localhost:8080/node/deleteNode`, {
                        path : node.path
                    }).then((response) => {
                        this.listLoading = false;
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getNodes();
                    });

                }).catch(() => {

                });
            },
            addNode: function () {
                this.addFormVisible = true;
                this.addForm = {
                    path: ''
                };
            },
            //新增
            addSubmit: function () {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.addLoading = true;
                            let node = {};
                            node.path = this.addForm.path
                            axios.post(`http://localhost:8080/node/createNode`, {
                                path : this.addForm.path
                            }).then((response) => {
                                this.paths = response.data.data;
                                this.addLoading = false;
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['addForm'].resetFields();
                                this.addFormVisible = false;
                                this.getNodes();
                            });
                        });
                    }
                });
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
            queryProperty: function (row, column, event) {
                console.info(row);
                console.info(column);
                console.info(event);
            }
        },
        mounted() {
            this.getNodes();
        }
    }

</script>

<style scoped>

</style>