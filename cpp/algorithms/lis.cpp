#include <bits/stdc++.h>

using namespace std;

int length_of_lis(vector<int>& a) {
    vector<int> dp(a.size());
    int len = 0;

    for (int ai : a) {
        auto it = lower_bound(dp.begin(), dp.begin() + len, ai);
        if (it == dp.begin() + len || *it != ai) {
            int idx = (int) (it - dp.begin());
            dp[idx] = ai;
            if (idx == len) len++;
        }
    }
    return len;
}