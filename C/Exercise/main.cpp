#include <iostream>
#include <set>
#include <random>
#include <thread>
#include <mutex>

using namespace std;

int main() {
    set<int> int_set;
    auto f = [&int_set]()
    {
        try
        {
            random_device rd;
            mt19937 gen(rd());
            uniform_int_distribution<> dis(1, 1000);
            for (size_t i = 0; i != 1000; ++i) {
                int_set.insert(dis(gen));
            }
        }catch (...){}
    };

    thread td1(f), td2(f);
    td1.join();
    td2.join();
    return 0;
}