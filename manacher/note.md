给出一个字符串，从中找出两个不相交且长度和最大的非空回文子串，输出长度和。  
```c++
#include <iostream>
#include<bits/stdc++.h>
#include <vector>
using namespace std;

void manacher(string &ss, vector<int> &leftPart){
    vector<int> t(ss.size(),-1);
    int middle=0;
    int rightBound=0;
    int len=ss.size();


    for(int i=1;i<len;++i){

        if(i<rightBound){
            int left=middle-(i-middle);
            t[i]=min(rightBound-i, t[left]);
        }
        while(i+t[i]<len && i-t[i]>=0 && ss[i+t[i]]==ss[i-t[i]]){
            if(ss[i+t[i]]=='#') leftPart[i+t[i]]=max(leftPart[i+t[i]],t[i]);
            else leftPart[i+t[i]]=max(leftPart[i+t[i]],t[i]+1);

            ++t[i];
        }
        --t[i];
        if(i+t[i]>rightBound){
            rightBound=i+t[i];
            middle=i;
        }
    }

}

int main(){
    string s;
    cin>>s;
    int N=s.size();
    if(N<2) {
        cout<<"0"<<endl;
        return 0;
    }

    int res=2;
    string ss="";
    for(int i=0;i<(int)s.size();++i){
        ss+="#";
        ss+=s[i];
    }
    ss+="#";
    int M=ss.size();
    vector<int> leftPart(M,0);
    vector<int> rightPart(M,0);
    for(int i=0;i<M;++i){
        if(ss[i]=='#'){
            leftPart[i]=0;
            rightPart[i]=0;
        }else{
            leftPart[i]=1;
            rightPart[i]=1;
        }
    }

    manacher(ss,leftPart);
    string reverses="";
    for(int i=M-1;i>=0;--i){
        reverses+=ss[i];
    }
    manacher(reverses,rightPart);
    for(int i=1;i<M;i++){
        leftPart[i]=max(leftPart[i],leftPart[i-1]);
        rightPart[i]=max(rightPart[i],rightPart[i-1]);
    }
    //for(int i=1;i<M;i+=2) cout<<leftPart[i]<<endl;
    for(int i=1;i<M-1;++i){
        //if(i&1) res=max(res,leftPart[i+1]+rightPart[M-1-i-1]-2);
        if(ss[i]=='#')    res=max(res,leftPart[i]+rightPart[M-1-i]);
    }
    //for(int i:leftPart) cout<<i<<endl;
    cout<<res<<endl;
    return 0;
}
````
