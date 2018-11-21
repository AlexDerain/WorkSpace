#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <stack>
#include <map>
#include <queue>
#include <sstream>
#include <unordered_map>
#include <cstring>
#include <fstream>
#include <cmath>

using namespace std;

vector<string> split(string text, char c){
    int index = 0;
    vector<string> result;
    for (int i = 0; i < text.length(); i++) {
        if (text[i] == c) {
            result.push_back(text.substr(index, i - index));
            index = i+1;
        }
        if (i == text.length()-1)
            result.push_back(text.substr(index, i - index+1));
    }
    return result;
}

/*
 * 从文件中加载数据集
 */
map<int, vector<vector<string>>> file2dataset(string filename){
    map<int, vector<vector<string>>> result;
    if (filename.empty()) return result;
    fstream fin(filename);
    if (!fin.is_open()) return result;
    vector<vector<string>> dataset;
    char buffer[256];
    while (!fin.eof()){
        fin.getline(buffer, 256, '\n');
        string line = string(buffer);
        if (line.empty()) continue;
        auto datas = split(line, '\t');
        vector<string> one;
        for (auto data : datas)
            one.push_back(data);
        dataset.push_back(one);
    }
    vector<vector<string>> labels = {
            vector<string>{"age"},
            vector<string>{"prescription"},
            vector<string>{"astigmatic"},
            vector<string>{"tearRate"}
    };

    result[1] = dataset;
    result[2] = labels;
    return result;
}

/*
 * 对一个数据集计算信息墒
 * (一个数据集的杂乱程度)
 */
double calc_shannonEnt(const vector<vector<string>>& dataset){
    map<string, int> labels;
    for (auto data : dataset){
        labels[data[data.size()-1]]++;
    }
    double shannonEnt = 0.0;
    auto it = labels.begin();
    for (; it != labels.end(); it++){
        double pi = double(it->second) / double(dataset.size());
        shannonEnt += (-pi * log10(pi)/log10(2));
    }
    return shannonEnt;
}

/*
 * 从大的数据集中把某一列值为value的数据集分离出来
 * dataset: 要分离的数据集
 * axis: 分离参考的列
 * value: 分离参考的列中的值
 * 注意：返回的结果数据集中不能再包含参考列了
 */
vector<vector<string>> split_dataset(const vector<vector<string>>& dataset, int axis, string value){
    vector<vector<string>> result;
    for (auto data : dataset){
        if (data[axis] != value)
            continue;
        vector<string> one;
        for (int i = 0; i < data.size(); i++){
            if (i == axis) continue;
            else one.push_back(data[i]);
        }
        result.push_back(one);
    }
    return result;
}

/*
 * 裁剪特征名集合
 */
vector<vector<string>> split_label(const vector<vector<string>>& label, int axis){
    vector<vector<string>> result;
    for (int i = 0; i < label.size(); i++){
        if (i == axis) continue;
        else result.push_back(label[i]);
    }
    return result;
}

/*
 * 找出某个特征的所有分支
 */
map<string, int> get_feature_classify(vector<vector<string>> dataset, int axis){
    map<string, int> result;
    for (auto data : dataset){
        result[data[axis]]++;
    }
    return result;
};

/*
 * 计算根据某一个特征划分数据集的信息增益
 */
double get_infogain(const vector<vector<string>> dataset, int axis){
    auto base_shannonEnt = calc_shannonEnt(dataset);
    auto target_shannonEnt_sum = 0.0;

    //找出这个特征的所有分支
    map<string, int> classify = get_feature_classify(dataset, axis);

    //计算信息增益
    auto it = classify.begin();
    for (; it != classify.end(); it++){
        auto split_datasets = split_dataset(dataset, axis, it->first);
        auto pi = double(split_datasets.size()) / double(dataset.size());
        auto shannonEnt = calc_shannonEnt(split_datasets);
        target_shannonEnt_sum += pi * shannonEnt;
    }

    return base_shannonEnt - target_shannonEnt_sum;
}

/*
 * 找出划分目前数据集的最好特征
 */
int get_best_split_feature(vector<vector<string>> dataset){
    double best_infogain = 0.0;
    int best_feature_axis;
    int feature_cnt = dataset[0].size()-1;

    for (int i = 0; i < feature_cnt; i++){
        auto infogain = get_infogain(dataset, i);
        if (best_infogain == 0){
            best_infogain = infogain;
            best_feature_axis = i;
        } else if (infogain > best_infogain){
            best_infogain = infogain;
            best_feature_axis = i;
        }
    }

    return best_feature_axis;
}

int str2int(const string& str){
    stringstream ss(str);
    int result;
    ss >> result;
    return result;
}

/*
 * 找出同一特征的集合出现最多的结果
 */
string get_largest_probability(vector<vector<string>> dataset, int axis, string value){
    map<string, int> result;
    for (auto data : dataset){
        if (data[axis] != value) continue;
        result[data[1]]++;
    }
    int max = 0;
    string v;
    auto it = result.begin();
    for (; it != result.end(); it++){
        if (it->second > max){
            max = it->second;
            v = it->first;
        }
    }
    return v;
}

struct Node{
    string value;
    vector<Node*> next;
    vector<string> property;
    Node(string v = "") : value(v) {}
};

/*
 * 构建决策树
 */
Node* create_tree(vector<vector<string>> dataset, vector<vector<string>> labels){
    Node* root = new Node;

    //只剩下最后一个特征的时候
    //这里可能涉及的情况比较多
    if (dataset[0].size() == 2){
        root->value = labels[0][0];
        auto feature_clssify = get_feature_classify(dataset, 0);
        auto it = feature_clssify.begin();
        for (; it != feature_clssify.end(); it++){
            //选择一个出现最多的结果作为这个分支的分类结果
            string result = get_largest_probability(dataset, 0, it->first);
            Node* node = new Node(result);
            root->next.push_back(node);
            root->property.push_back(it->first);
        }
        return root;
    }

    //找出划分目前数据集最好的特征
    int axis = get_best_split_feature(dataset);
    root->value = labels[axis][0];

    //找出这个最好特征列的所有分支
    auto feature_classify = get_feature_classify(dataset, axis);

    //根据不同的分支继续建树
    auto it = feature_classify.begin();
    for (; it != feature_classify.end(); it++){
        auto sub_dataset = split_dataset(dataset, axis, it->first);
        Node* node = nullptr;

        //如果信息熵为0，则该分支下的数据集都为同一类型，不用继续分类
        if (calc_shannonEnt(sub_dataset) == 0){
            string result = sub_dataset[0][sub_dataset[0].size()-1];
            node = new Node(result);

            //如果信息熵不为0，则说明该分支下有不同类型的数据集，继续分类
        }else {
            auto sub_label = split_label(labels, axis);
            node = create_tree(sub_dataset, sub_label);
        }
        root->next.push_back(node);
        root->property.push_back(it->first);
    }

    return root;
};

/*
 * 找出决策树中所有节点儿子最多的个数
 */
void get_max_ch_len(Node* root, int& result){
    if (!root) return;
    int ch_len = root->next.size();
    if (ch_len > result)
        result = ch_len;
    for (auto node : root->next){
        get_max_ch_len(node, result);
    }
}

map<Node*, int> indent;
int pre = 0;
int index = 0;
int layer = 0;
map<Node*,int> m_l;
map<int, int> judge;

/*
 * 初始化输出树形决策树
 */
void init_tree(Node* root, int& max_ch_len, int layer){
    if (!root) return;
    m_l[root] = layer;
    for (auto node : root->next){
        init_tree(node, max_ch_len, layer+1);
        if (!indent[root])
            indent[root] = pre++;
        else
            indent[root]++;
    }
    pre += (max_ch_len - root->next.size());
    if (!indent[root])
        indent[root] = pre++;
}

/*
 * 输出决策树模型
 */
void show_dtree(Node* root){
    if (!root) return;
    int max_ch_len = 0;
    get_max_ch_len(root, max_ch_len);
    init_tree(root, max_ch_len, 0);
    queue<Node*> q;
    q.push(root);
    fstream fout("cout.txt");
    while (!q.empty()){
        auto top = q.front();
        q.pop();
        if (!judge[m_l[top]]){
            judge[m_l[top]] = 1;
            fout << endl << endl;
            index = 0;
        }
        for (; index < indent[top]; index++) fout << "  ";

        if (!top->next.size()) fout << "*";
        if (top->next.size()) fout << "/";
        fout << top->value << " ";
        if (top->next.size()) fout << "\\";
        if (!top->next.size()) fout << "*";
        if (top->next.size())
            fout << "分支:" << " ";
        for (int i = 0; i < top->property.size(); i++) fout << i+1 << " : " << top->property[i] << ' ';
        for (auto node : top->next)
            q.push(node);
    }
}

/*
 * 进行分类
 */
string predict(Node* tree, vector<string> labels, vector<string> data){
    while(tree->next.size()) {
        bool judge = true;
        for (int i = 0; i < labels.size(); i++) {
            if (labels[i] == tree->value) {
                for (int j = 0; j < tree->property.size(); j++) {
                    if (data[i] == tree->property[j]) {
                        tree = tree->next[j];
                        judge = false;
                        break;
                    }
                }
                break;
            }
        }
        if (judge) return "unknown";
    }
    return tree->value;
}

int main(){
    auto result = file2dataset("lenses.txt");
    auto dataset = result[1];
    auto labels = result[2];
    Node* tree = create_tree(dataset, labels);

    show_dtree(tree);

    vector<string> data1{"pre", "myope", "no", "normal"};
    vector<string> data2{"pre", "myope", "no", "reduced"};
    vector<string> data3{"presbyopic", "myope", "yes", "normal"};
    vector<string> data4{"1", "2", "3", "4"};

    vector<string> label = {"age", "prescription", "astigmatic", "tearRate"};

    cout << predict(tree, label, data1) << endl;
    cout << predict(tree, label, data2) << endl;
    cout << predict(tree, label, data3) << endl;
    cout << predict(tree, label, data4) << endl;

    return 0;
}