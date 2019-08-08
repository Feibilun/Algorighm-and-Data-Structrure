package KMP;

import java.util.Arrays;

public class StrstrDemo {
    public static void main(String[] args) {
        String pattern="aabaaac";
        int[] next=getPrefix(pattern.toCharArray(),pattern.length());
        System.out.println(Arrays.toString(next));
        String haystack="aabaaabaaac";
        System.out.println(strStr(haystack,pattern));
    }

    public static int strStr(String haystack, String needle) {
        char[] hh=haystack.toCharArray();
        char[] nn=needle.toCharArray();
        if(nn.length==0) return 0;
        int[] next=getPrefix(nn,nn.length);
        int i=0;
        int j=0;
        while(i<hh.length){
            if(hh[i]==nn[j]){
                if(j==nn.length-1){
                    return i-nn.length+1;
                }
                i++;
                j++;
            }else{
                if(j==0){
                    i++;
                }else{
                    j=next[j-1];
                }
            }
        }
        return -1;
    }


    //获取表是关键，两种写法
    public static int[] getPrefix(char[] cc,int n){
        int[] next=new int[n];
        next[0]=0;
        int len=0;
        int i=1;
        while(i<n){
            if(cc[i]==cc[len]){
                len++;
                next[i]=len;
                i++;
            }else{
                if(len==0){
                    next[i]=0;
                    i++;
                }else{
                    len=next[len-1];
                }
            }
        }
        return next;
    }

    public static int[] getTable(char[] tt,int n){
        int i=1;
        int[] table=new int[n];
        table[0]=0;
        while(i<n){
            int prelen=table[i-1];
            if(tt[i]==tt[prelen]){
                table[i]=prelen+1;
            }else{
                if(prelen==0){
                    table[i]=0;
                }else{
                    prelen=table[prelen-1];
                    while(prelen>0 && tt[i]!=tt[prelen]){
                        prelen=table[prelen-1];
                    }
                    if(prelen==0 && tt[i]!=tt[0]){
                        table[i]=0;
                    }else{
                        table[i]=prelen+1;
                    }
                }
            }
            i++;
        }
        return table;
    }
}
