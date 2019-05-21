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
                <el-form-item label="配置名称" prop="itemName">
                    <el-input v-model="addForm.path" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="配置类型">
                    <el-select v-model="propertyValueType" placeholder="请选择">
                        <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="配置值" prop="itemValue">
                    <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" v-model="addForm.itemValue" auto-complete="off"></el-input>
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
                configs: [],
                total: 0,
                page: 1,
                listLoading: false,
                sels: [],//列表选中列
                
                /**
                 * 新增窗口
                 */
                addForm: {
                    itemName: '',
                    itemValue: ''
                },
                addFormVisible: false,
                addLoading: false,
                addFormRules: {
                    itemName: [
                       { required: true, message: '请输入配置名称', trigger: 'blur' } 
                    ],
                    itemValue: [
                       { required: true, message: '请输入配置值', trigger: 'blur' } 
                    ]
                },
                /**
                 * 编辑窗口
                 */
                editForm: {
                    itemName: '',
                    itemValue: ''
                },
                editFormVisible: false,
                editLoading: false,
                editFormRules: {
                    itemName: [
                       { required: true, message: '请输入配置名称', trigger: 'blur' } 
                    ],
                    itemValue: [
                       { required: true, message: '请输入配置值', trigger: 'blur' } 
                    ]
                }

                
            }
        },
        methods: {
            handleCurrentChange(val) {
                this.page = val;
                this.getNodeProperty();
            },
            /**
             * 获取节点配置信息
             */
            getNodeProperty() {
                let path = this.$route.params.path;
                this.listLoading = true;
                axios.post(`http://localhost:8080/property/getProperty`, {
                    path : path
                }).then((response) => {
                    console.info(response.data.data);
                    this.listLoading = false;
                })
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
                        this.getNodeProperty();
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
                                this.getNodeProperty();
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
            this.getNodeProperty();
        }
    }

</script>

<style scoped>

</style>