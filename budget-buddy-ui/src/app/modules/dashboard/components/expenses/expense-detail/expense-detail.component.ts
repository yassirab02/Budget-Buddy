import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ExpensesService} from '../../../../../services/services/expenses.service';
import {FindExpenseById$Params} from '../../../../../services/fn/expenses/find-expense-by-id';
import {PageResponseExpensesResponse} from '../../../../../services/models/page-response-expenses-response';
import {ExpensesResponse} from '../../../../../services/models/expenses-response';
import {NoteService} from '../../../../../services/services/note.service';
import {CreateNote$Params} from '../../../../../services/fn/note/create-note';
import {NoteRequest} from '../../../../../services/models/note-request';
import {DeleteNote$Params} from '../../../../../services/fn/note/delete-note';

@Component({
  selector: 'app-expense-detail',
  templateUrl: './expense-detail.component.html',
  styleUrl: './expense-detail.component.css'
})
export class ExpenseDetailComponent  implements OnInit{
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  expensesResponse: ExpensesResponse = {};  // Store the actual wallet
  noteRequest:NoteRequest = {
    title: '',
    content: ''
  }
  isLoading=true;
  showSuccess = false;

  constructor(
    private expenseService: ExpensesService,
    private noteService: NoteService,
    private route: ActivatedRoute

  ) {
  }

  ngOnInit() {
    const expenseId = this.route.snapshot.paramMap.get('id');
    if (expenseId) {
      this.findExpenseById(Number(expenseId));
    }
  }

  findExpenseById(expenseId: number) {
    const params : FindExpenseById$Params = {'expense-id': expenseId};
    this.expenseService.findExpenseById(params).subscribe({
      next: (expense) => {
        this.expensesResponse = expense;
        this.isLoading=false;
      },
      error: (err) => {
        this.message = 'An error occurred while fetching the expense.';
        this.level = 'error';
      }
    });
  }

  addNote() {
    this.noteRequest.expenseId = this.expensesResponse.id;
    const params: CreateNote$Params = { body: this.noteRequest };
    // Reset errors and message before making the request
    this.errorMsg = [];
    this.noteService.createNote(params).subscribe({
      next: () => {
        this.noteRequest = { title: '', content: '' }; // Clear input fields
        if (this.expensesResponse.id === undefined) {
          return;
        }
        this.findExpenseById(this.expensesResponse.id); // Fetch updated expense data
      },
      error: (err) => {
        console.error('Error adding note:', err);
        this.errorMsg.push(err.error.error);
        this.level = 'error';
      }
    });
  }

  deleteNote(id: number | undefined) {
    if (id === undefined) {
      return;
    }
    const params: DeleteNote$Params = { 'note-id': id };

    this.noteService.deleteNote(params).subscribe({
      next: () => {
        if (this.expensesResponse.id===undefined) {
          return;
        }
        this.findExpenseById(this.expensesResponse.id);
      },
      error: (err) => {
        console.error('Error deleting note:', err);
        this.errorMsg.push(err.error.error);
        this.level = 'error';
      }
    });
  }

}
