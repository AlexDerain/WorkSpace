#include <iostream>
#include <random>
#include <mutex>
#include <thread>

using namespace std;

int main() {
    vector<int> int_set;
    mutex mt;
    auto f = [&int_set, &mt]
    {
        try
        {
            random_device rd;
            mt19937 gen(rd());
            uniform_int_distribution<> dis(1, 20);
            for(size_t i = 0; i != 20; ++i)
            {
                mt.lock();
                int k = dis(gen);
                cout << k << " by thread: " << this_thread::get_id() << ", hello!"<< endl;
                int_set.push_back(k);
                mt.unlock();
            }
        }catch(...) {}
    };

    thread td1(f), td2(f);
    td1.join();
    td2.join();
    return 0;
}