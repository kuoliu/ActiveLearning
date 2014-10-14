A = load('precision.txt');
B = load('recall.txt');
C = load('accuracy.txt');

plot(A(:,1),A(:,2), 'b');
hold on;
plot(B(:,1),B(:,2), 'r');
hold on;
plot(C(:,1),C(:,2), 'g');
legend('precision','recall', 'accuracy');