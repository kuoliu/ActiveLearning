A = load('matlab\precision.txt');
B = load('matlab\recall.txt');
C = load('matlab\accuracy.txt');
D = load('matlab\fMeasure.txt');

% A = load('precision.txt');
% B = load('recall.txt');
% C = load('accuracy.txt');
% D = load('fMeasure.txt');

figure(2)
bar([A(:,2), B(:,2),C(:,2), D(:,2)])
legend('precision','recall', 'accuracy','F0.5');
xlabel('round');
ylabel('percentage');
title('Precision Recall Accuracy F0.5 Bar Plot');
print(2,'-djpeg','bar.jpeg')