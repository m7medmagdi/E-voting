package com.example.e_voting.AdminPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Adapters.NewsMessages;
import com.example.e_voting.Dialogs.DialogTimeAndDate;
import com.example.e_voting.Dialogs.DialogTimeAndDate222;
import com.example.e_voting.Dialogs.DialogTimeAndDate333;
import com.example.e_voting.Model.MessageModel;
import com.example.e_voting.Model.Winers;
import com.example.e_voting.Notification.FcmNotificationsSender;
import com.example.e_voting.R;
import com.example.e_voting.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Home_Admin extends AppCompatActivity implements DialogTimeAndDate.DialogListenerTimeDate, DialogTimeAndDate333.DialogTimeAndDateLister333, DialogTimeAndDate222.DialogTimeAndDateListener222 {

    private DatabaseReference reference, table_user, currentStage, currentMembers, dbEqualsVotes;
    FirebaseDatabase database, db;
    private String namewin, countwin;

    TextView txt_winner1, txt_winner2;
    TextView tv_currentStage;

    String notDateNomination, dateEqualVote, dateVote, dataNomination, timeNomination;

    DrawerLayout drawerLayout;

    private RecyclerView recyclerView;
    private ArrayList<MessageModel> list;
    private NewsMessages notificationAdapter;
    private ValueEventListener currentStageListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            Calendar myCalendar = Calendar.getInstance();
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);
            int month = myCalendar.get(Calendar.MONTH);
            int year = myCalendar.get(Calendar.YEAR);
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);

            int databaseYear = Integer.parseInt(snapshot.child("nomination").child("date").child("year").getValue().toString());
            int databaseMonth = Integer.parseInt(snapshot.child("nomination").child("date").child("month").getValue().toString());
            int databaseDay = Integer.parseInt(snapshot.child("nomination").child("date").child("day").getValue().toString());
            int databaseHour = Integer.parseInt(snapshot.child("nomination").child("time").child("hour").getValue().toString());
            int databaseMinute = Integer.parseInt(snapshot.child("nomination").child("time").child("minute").getValue().toString());

            int VoteDatabaseYear = Integer.parseInt(snapshot.child("vote").child("date").child("year").getValue().toString());
            int VoteDatabaseMonth = Integer.parseInt(snapshot.child("vote").child("date").child("month").getValue().toString());
            int VoteDatabaseDay = Integer.parseInt(snapshot.child("vote").child("date").child("day").getValue().toString());
            int VoteDatabaseHour = Integer.parseInt(snapshot.child("vote").child("time").child("hour").getValue().toString());
            int VoteDatabaseMinute = Integer.parseInt(snapshot.child("vote").child("time").child("minute").getValue().toString());

            int EqualVoteDatabaseYear = Integer.parseInt(snapshot.child("??????????????").child("date").child("year").getValue().toString());
            int EqualVoteDatabaseMonth = Integer.parseInt(snapshot.child("??????????????").child("date").child("month").getValue().toString());
            int EqualVoteDatabaseDay = Integer.parseInt(snapshot.child("??????????????").child("date").child("day").getValue().toString());
            int EqualVoteDatabaseHour = Integer.parseInt(snapshot.child("??????????????").child("time").child("hour").getValue().toString());
            int EqualVoteDatabaseMinute = Integer.parseInt(snapshot.child("??????????????").child("time").child("minute").getValue().toString());

            if (databaseYear < year) {
//                    ???????????? ??????
                if (VoteDatabaseYear < year) {
//                        ?????????????? ??????
                    if (EqualVoteDatabaseYear < year) {
                        tv_currentStage.setText("?????????????? ????????????????");
                    } else if (EqualVoteDatabaseYear > year) {
                        tv_currentStage.setText("???????? ??????????????");
                    } else if (EqualVoteDatabaseYear == year) {
                        if (EqualVoteDatabaseMonth < month) {
                            tv_currentStage.setText("?????????????? ????????????????");
                        } else if (EqualVoteDatabaseMonth > month) {
                            tv_currentStage.setText("???????? ??????????????");
                        } else if (EqualVoteDatabaseMonth == month) {
                            if (EqualVoteDatabaseDay < day) {
                                tv_currentStage.setText("?????????????? ????????????????");
                            } else if (EqualVoteDatabaseDay > day) {
                                tv_currentStage.setText("???????? ??????????????");
                            } else if (EqualVoteDatabaseDay == day) {
                                if (EqualVoteDatabaseHour < hour) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseHour > hour) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseHour == hour) {
                                    if (EqualVoteDatabaseMinute < minute) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseMinute > minute) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    }
                                }
                            }
                        }
                    }
                } else if (VoteDatabaseYear > year) {
                    tv_currentStage.setText("?????????? ??????????????");
                } else if (VoteDatabaseYear == year) {
                    if (VoteDatabaseMonth < month) {
//                            ?????????????? ??????
                        if (EqualVoteDatabaseYear < year) {
                            tv_currentStage.setText("?????????????? ????????????????");
                        } else if (EqualVoteDatabaseYear > year) {
                            tv_currentStage.setText("???????? ??????????????");
                        } else if (EqualVoteDatabaseYear == year) {
                            if (EqualVoteDatabaseMonth < month) {
                                tv_currentStage.setText("?????????????? ????????????????");
                            } else if (EqualVoteDatabaseMonth > month) {
                                tv_currentStage.setText("???????? ??????????????");
                            } else if (EqualVoteDatabaseMonth == month) {
                                if (EqualVoteDatabaseDay < day) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseDay > day) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseDay == day) {
                                    if (EqualVoteDatabaseHour < hour) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseHour > hour) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseHour == hour) {
                                        if (EqualVoteDatabaseMinute < minute) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMinute > minute) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        }
                                    }
                                }
                            }
                        }
                    } else if (VoteDatabaseMonth > month) {
                        tv_currentStage.setText("?????????? ??????????????");
                    } else if (VoteDatabaseMonth == month) {
                        if (VoteDatabaseDay < day) {
//                                ?????????????? ??????
                            if (EqualVoteDatabaseYear < year) {
                                tv_currentStage.setText("?????????????? ????????????????");
                            } else if (EqualVoteDatabaseYear > year) {
                                tv_currentStage.setText("???????? ??????????????");
                            } else if (EqualVoteDatabaseYear == year) {
                                if (EqualVoteDatabaseMonth < month) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseMonth > month) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseMonth == month) {
                                    if (EqualVoteDatabaseDay < day) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseDay > day) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseDay == day) {
                                        if (EqualVoteDatabaseHour < hour) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseHour > hour) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseHour == hour) {
                                            if (EqualVoteDatabaseMinute < minute) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMinute > minute) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (VoteDatabaseDay > day) {
                            tv_currentStage.setText("?????????? ??????????????");
                        } else if (VoteDatabaseDay == day) {
                            if (VoteDatabaseHour < hour) {
//                                    ?????????????? ??????
                                if (EqualVoteDatabaseYear < year) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseYear > year) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseYear == year) {
                                    if (EqualVoteDatabaseMonth < month) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseMonth > month) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseMonth == month) {
                                        if (EqualVoteDatabaseDay < day) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseDay > day) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseDay == day) {
                                            if (EqualVoteDatabaseHour < hour) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseHour > hour) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseHour == hour) {
                                                if (EqualVoteDatabaseMinute < minute) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMinute > minute) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (VoteDatabaseHour > hour) {
                                tv_currentStage.setText("?????????? ??????????????");
                            } else if (VoteDatabaseHour == hour) {
                                if (VoteDatabaseMinute < minute) {
//                                        ???????????? ??????
                                    if (EqualVoteDatabaseYear < year) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseYear > year) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseYear == year) {
                                        if (EqualVoteDatabaseMonth < month) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMonth > month) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseMonth == month) {
                                            if (EqualVoteDatabaseDay < day) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseDay > day) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseDay == day) {
                                                if (EqualVoteDatabaseHour < hour) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseHour > hour) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseHour == hour) {
                                                    if (EqualVoteDatabaseMinute < minute) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMinute > minute) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (VoteDatabaseMinute > minute) {
                                    tv_currentStage.setText("?????????? ??????????????");
                                }
                            }
                        }
                    }
                }
//     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else if (databaseYear > year) {
                tv_currentStage.setText("?????????? ????????????");
            } else if (databaseYear == year) {
                if (databaseMonth < month) {
//                       ???????????? ??????
                    if (VoteDatabaseYear < year) {
//                            ?????????????? ??????
                        if (EqualVoteDatabaseYear < year) {
                            tv_currentStage.setText("?????????????? ????????????????");
                        } else if (EqualVoteDatabaseYear > year) {
                            tv_currentStage.setText("???????? ??????????????");
                        } else if (EqualVoteDatabaseYear == year) {
                            if (EqualVoteDatabaseMonth < month) {
                                tv_currentStage.setText("?????????????? ????????????????");
                            } else if (EqualVoteDatabaseMonth > month) {
                                tv_currentStage.setText("???????? ??????????????");
                            } else if (EqualVoteDatabaseMonth == month) {
                                if (EqualVoteDatabaseDay < day) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseDay > day) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseDay == day) {
                                    if (EqualVoteDatabaseHour < hour) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseHour > hour) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseHour == hour) {
                                        if (EqualVoteDatabaseMinute < minute) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMinute > minute) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        }
                                    }
                                }
                            }
                        }
                    } else if (VoteDatabaseYear > year) {
                        tv_currentStage.setText("?????????? ??????????????");
                    } else if (VoteDatabaseYear == year) {
                        if (VoteDatabaseMonth < month) {
//                                ?????????????? ??????
                            if (EqualVoteDatabaseYear < year) {
                                tv_currentStage.setText("?????????????? ????????????????");
                            } else if (EqualVoteDatabaseYear > year) {
                                tv_currentStage.setText("???????? ??????????????");
                            } else if (EqualVoteDatabaseYear == year) {
                                if (EqualVoteDatabaseMonth < month) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseMonth > month) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseMonth == month) {
                                    if (EqualVoteDatabaseDay < day) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseDay > day) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseDay == day) {
                                        if (EqualVoteDatabaseHour < hour) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseHour > hour) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseHour == hour) {
                                            if (EqualVoteDatabaseMinute < minute) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMinute > minute) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (VoteDatabaseMonth > month) {
                            tv_currentStage.setText("?????????? ??????????????");
                        } else if (VoteDatabaseMonth == month) {
                            if (VoteDatabaseDay < day) {
//                                    ?????????????? ??????
                                if (EqualVoteDatabaseYear < year) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseYear > year) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseYear == year) {
                                    if (EqualVoteDatabaseMonth < month) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseMonth > month) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseMonth == month) {
                                        if (EqualVoteDatabaseDay < day) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseDay > day) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseDay == day) {
                                            if (EqualVoteDatabaseHour < hour) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseHour > hour) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseHour == hour) {
                                                if (EqualVoteDatabaseMinute < minute) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMinute > minute) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (VoteDatabaseDay > day) {
                                tv_currentStage.setText("?????????? ??????????????");
                            } else if (VoteDatabaseDay == day) {
                                if (VoteDatabaseHour < hour) {
//                                        ?????????????? ??????
                                    if (EqualVoteDatabaseYear < year) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseYear > year) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseYear == year) {
                                        if (EqualVoteDatabaseMonth < month) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMonth > month) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseMonth == month) {
                                            if (EqualVoteDatabaseDay < day) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseDay > day) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseDay == day) {
                                                if (EqualVoteDatabaseHour < hour) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseHour > hour) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseHour == hour) {
                                                    if (EqualVoteDatabaseMinute < minute) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMinute > minute) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (VoteDatabaseHour > hour) {
                                    tv_currentStage.setText("?????????? ??????????????");
                                } else if (VoteDatabaseHour == hour) {
                                    if (VoteDatabaseMinute < minute) {
//                                            ?????????????? ??????
                                        if (EqualVoteDatabaseYear < year) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseYear > year) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseYear == year) {
                                            if (EqualVoteDatabaseMonth < month) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMonth > month) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseMonth == month) {
                                                if (EqualVoteDatabaseDay < day) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseDay > day) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseDay == day) {
                                                    if (EqualVoteDatabaseHour < hour) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseHour > hour) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseHour == hour) {
                                                        if (EqualVoteDatabaseMinute < minute) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseMinute > minute) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (VoteDatabaseMinute > minute) {
                                        tv_currentStage.setText("?????????? ??????????????");
                                    }
                                }
                            }
                        }
                    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                } else if (databaseMonth > month) {
                    tv_currentStage.setText("?????????? ????????????");
                } else if (databaseMonth == month) {
                    if (databaseDay < day) {
//                            ???????????? ??????
                        if (VoteDatabaseYear < year) {
//                                ?????????????? ??????
                            if (EqualVoteDatabaseYear < year) {
                                tv_currentStage.setText("?????????????? ????????????????");
                            } else if (EqualVoteDatabaseYear > year) {
                                tv_currentStage.setText("???????? ??????????????");
                            } else if (EqualVoteDatabaseYear == year) {
                                if (EqualVoteDatabaseMonth < month) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseMonth > month) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseMonth == month) {
                                    if (EqualVoteDatabaseDay < day) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseDay > day) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseDay == day) {
                                        if (EqualVoteDatabaseHour < hour) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseHour > hour) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseHour == hour) {
                                            if (EqualVoteDatabaseMinute < minute) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMinute > minute) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (VoteDatabaseYear > year) {
                            tv_currentStage.setText("?????????? ??????????????");
                        } else if (VoteDatabaseYear == year) {
                            if (VoteDatabaseMonth < month) {
//                                    ?????????????? ??????
                                if (EqualVoteDatabaseYear < year) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseYear > year) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseYear == year) {
                                    if (EqualVoteDatabaseMonth < month) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseMonth > month) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseMonth == month) {
                                        if (EqualVoteDatabaseDay < day) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseDay > day) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseDay == day) {
                                            if (EqualVoteDatabaseHour < hour) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseHour > hour) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseHour == hour) {
                                                if (EqualVoteDatabaseMinute < minute) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMinute > minute) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (VoteDatabaseMonth > month) {
                                tv_currentStage.setText("?????????? ??????????????");
                            } else if (VoteDatabaseMonth == month) {
                                if (VoteDatabaseDay < day) {
//                                        ?????????????? ??????
                                    if (EqualVoteDatabaseYear < year) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseYear > year) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseYear == year) {
                                        if (EqualVoteDatabaseMonth < month) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMonth > month) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseMonth == month) {
                                            if (EqualVoteDatabaseDay < day) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseDay > day) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseDay == day) {
                                                if (EqualVoteDatabaseHour < hour) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseHour > hour) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseHour == hour) {
                                                    if (EqualVoteDatabaseMinute < minute) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMinute > minute) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    }
                                                }
                                            }
                                        }
                                    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                } else if (VoteDatabaseDay > day) {
                                    tv_currentStage.setText("?????????? ??????????????");
                                } else if (VoteDatabaseDay == day) {
                                    if (VoteDatabaseHour < hour) {
//                                            ?????????????? ??????
                                        if (EqualVoteDatabaseYear < year) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseYear > year) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseYear == year) {
                                            if (EqualVoteDatabaseMonth < month) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMonth > month) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseMonth == month) {
                                                if (EqualVoteDatabaseDay < day) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseDay > day) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseDay == day) {
                                                    if (EqualVoteDatabaseHour < hour) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseHour > hour) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseHour == hour) {
                                                        if (EqualVoteDatabaseMinute < minute) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseMinute > minute) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        }
                                                    }
                                                }
                                            }
                                        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    } else if (VoteDatabaseHour > hour) {
                                        tv_currentStage.setText("?????????? ??????????????");
                                    } else if (VoteDatabaseHour == hour) {
                                        if (VoteDatabaseMinute < minute) {
//                                                ?????????????? ??????
                                            if (EqualVoteDatabaseYear < year) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseYear > year) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseYear == year) {
                                                if (EqualVoteDatabaseMonth < month) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMonth > month) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseMonth == month) {
                                                    if (EqualVoteDatabaseDay < day) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseDay > day) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseDay == day) {
                                                        if (EqualVoteDatabaseHour < hour) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseHour > hour) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        } else if (EqualVoteDatabaseHour == hour) {
                                                            if (EqualVoteDatabaseMinute < minute) {
                                                                tv_currentStage.setText("?????????????? ????????????????");
                                                            } else if (EqualVoteDatabaseMinute > minute) {
                                                                tv_currentStage.setText("???????? ??????????????");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (VoteDatabaseMinute > minute) {
                                            tv_currentStage.setText("?????????? ??????????????");
                                        }
                                    }
                                }
                            }
                        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    } else if (databaseDay > day) {
                        tv_currentStage.setText("?????????? ????????????");
                    } else if (databaseDay == day) {
                        if (databaseHour < hour) {
//                                ???????????? ??????
                            if (VoteDatabaseYear < year) {
//                                    ?????????????? ??????
                                if (EqualVoteDatabaseYear < year) {
                                    tv_currentStage.setText("?????????????? ????????????????");
                                } else if (EqualVoteDatabaseYear > year) {
                                    tv_currentStage.setText("???????? ??????????????");
                                } else if (EqualVoteDatabaseYear == year) {
                                    if (EqualVoteDatabaseMonth < month) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseMonth > month) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseMonth == month) {
                                        if (EqualVoteDatabaseDay < day) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseDay > day) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseDay == day) {
                                            if (EqualVoteDatabaseHour < hour) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseHour > hour) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseHour == hour) {
                                                if (EqualVoteDatabaseMinute < minute) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMinute > minute) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                }
                                            }
                                        }
                                    }
                                }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            } else if (VoteDatabaseYear > year) {
                                tv_currentStage.setText("?????????? ??????????????");
                            } else if (VoteDatabaseYear == year) {
                                if (VoteDatabaseMonth < month) {
//                                        ?????????????? ??????
                                    if (EqualVoteDatabaseYear < year) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseYear > year) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseYear == year) {
                                        if (EqualVoteDatabaseMonth < month) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMonth > month) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseMonth == month) {
                                            if (EqualVoteDatabaseDay < day) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseDay > day) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseDay == day) {
                                                if (EqualVoteDatabaseHour < hour) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseHour > hour) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseHour == hour) {
                                                    if (EqualVoteDatabaseMinute < minute) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMinute > minute) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    }
                                                }
                                            }
                                        }
                                    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                } else if (VoteDatabaseMonth > month) {
                                    tv_currentStage.setText("?????????? ??????????????");
                                } else if (VoteDatabaseMonth == month) {
                                    if (VoteDatabaseDay < day) {
//                                            ?????????????? ??????
                                        if (EqualVoteDatabaseYear < year) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseYear > year) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseYear == year) {
                                            if (EqualVoteDatabaseMonth < month) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMonth > month) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseMonth == month) {
                                                if (EqualVoteDatabaseDay < day) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseDay > day) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseDay == day) {
                                                    if (EqualVoteDatabaseHour < hour) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseHour > hour) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseHour == hour) {
                                                        if (EqualVoteDatabaseMinute < minute) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseMinute > minute) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        }
                                                    }
                                                }
                                            }
                                        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    } else if (VoteDatabaseDay > day) {
                                        tv_currentStage.setText("?????????? ??????????????");
                                    } else if (VoteDatabaseDay == day) {
                                        if (VoteDatabaseHour < hour) {
//                                                ?????????????? ??????
                                            if (EqualVoteDatabaseYear < year) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseYear > year) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseYear == year) {
                                                if (EqualVoteDatabaseMonth < month) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMonth > month) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseMonth == month) {
                                                    if (EqualVoteDatabaseDay < day) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseDay > day) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseDay == day) {
                                                        if (EqualVoteDatabaseHour < hour) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseHour > hour) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        } else if (EqualVoteDatabaseHour == hour) {
                                                            if (EqualVoteDatabaseMinute < minute) {
                                                                tv_currentStage.setText("?????????????? ????????????????");
                                                            } else if (EqualVoteDatabaseMinute > minute) {
                                                                tv_currentStage.setText("???????? ??????????????");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                        } else if (VoteDatabaseHour > hour) {
                                            tv_currentStage.setText("?????????? ??????????????");
                                        } else if (VoteDatabaseHour == hour) {
                                            if (VoteDatabaseMinute < minute) {
//                                                    ?????????????? ??????
                                                if (EqualVoteDatabaseYear < year) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseYear > year) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseYear == year) {
                                                    if (EqualVoteDatabaseMonth < month) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMonth > month) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseMonth == month) {
                                                        if (EqualVoteDatabaseDay < day) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseDay > day) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        } else if (EqualVoteDatabaseDay == day) {
                                                            if (EqualVoteDatabaseHour < hour) {
                                                                tv_currentStage.setText("?????????????? ????????????????");
                                                            } else if (EqualVoteDatabaseHour > hour) {
                                                                tv_currentStage.setText("???????? ??????????????");
                                                            } else if (EqualVoteDatabaseHour == hour) {
                                                                if (EqualVoteDatabaseMinute < minute) {
                                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                                } else if (EqualVoteDatabaseMinute > minute) {
                                                                    tv_currentStage.setText("???????? ??????????????");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (VoteDatabaseMinute > minute) {
                                                tv_currentStage.setText("?????????? ??????????????");
                                            }
                                        }
                                    }
                                }
                            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        } else if (databaseHour > hour) {
                            tv_currentStage.setText("?????????? ????????????");
                        } else if (databaseHour == hour) {
                            if (databaseMinute < minute) {
//                                   ???????????? ??????
                                if (VoteDatabaseYear < year) {
//                                        ?????????????? ??????
                                    if (EqualVoteDatabaseYear < year) {
                                        tv_currentStage.setText("?????????????? ????????????????");
                                    } else if (EqualVoteDatabaseYear > year) {
                                        tv_currentStage.setText("???????? ??????????????");
                                    } else if (EqualVoteDatabaseYear == year) {
                                        if (EqualVoteDatabaseMonth < month) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseMonth > month) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseMonth == month) {
                                            if (EqualVoteDatabaseDay < day) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseDay > day) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseDay == day) {
                                                if (EqualVoteDatabaseHour < hour) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseHour > hour) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseHour == hour) {
                                                    if (EqualVoteDatabaseMinute < minute) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMinute > minute) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (VoteDatabaseYear > year) {
                                    tv_currentStage.setText("?????????? ??????????????");
                                } else if (VoteDatabaseYear == year) {
                                    if (VoteDatabaseMonth < month) {
//                                            ?????????????? ??????
                                        if (EqualVoteDatabaseYear < year) {
                                            tv_currentStage.setText("?????????????? ????????????????");
                                        } else if (EqualVoteDatabaseYear > year) {
                                            tv_currentStage.setText("???????? ??????????????");
                                        } else if (EqualVoteDatabaseYear == year) {
                                            if (EqualVoteDatabaseMonth < month) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseMonth > month) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseMonth == month) {
                                                if (EqualVoteDatabaseDay < day) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseDay > day) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseDay == day) {
                                                    if (EqualVoteDatabaseHour < hour) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseHour > hour) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseHour == hour) {
                                                        if (EqualVoteDatabaseMinute < minute) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseMinute > minute) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (VoteDatabaseMonth > month) {
                                        tv_currentStage.setText("?????????? ??????????????");
                                    } else if (VoteDatabaseMonth == month) {
                                        if (VoteDatabaseDay < day) {
//                                                ?????????????? ??????
                                            if (EqualVoteDatabaseYear < year) {
                                                tv_currentStage.setText("?????????????? ????????????????");
                                            } else if (EqualVoteDatabaseYear > year) {
                                                tv_currentStage.setText("???????? ??????????????");
                                            } else if (EqualVoteDatabaseYear == year) {
                                                if (EqualVoteDatabaseMonth < month) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseMonth > month) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseMonth == month) {
                                                    if (EqualVoteDatabaseDay < day) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseDay > day) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseDay == day) {
                                                        if (EqualVoteDatabaseHour < hour) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseHour > hour) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        } else if (EqualVoteDatabaseHour == hour) {
                                                            if (EqualVoteDatabaseMinute < minute) {
                                                                tv_currentStage.setText("?????????????? ????????????????");
                                                            } else if (EqualVoteDatabaseMinute > minute) {
                                                                tv_currentStage.setText("???????? ??????????????");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (VoteDatabaseDay > day) {
                                            tv_currentStage.setText("?????????? ??????????????");
                                        } else if (VoteDatabaseDay == day) {
                                            if (VoteDatabaseHour < hour) {
//                                                    ?????????????? ??????
                                                if (EqualVoteDatabaseYear < year) {
                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                } else if (EqualVoteDatabaseYear > year) {
                                                    tv_currentStage.setText("???????? ??????????????");
                                                } else if (EqualVoteDatabaseYear == year) {
                                                    if (EqualVoteDatabaseMonth < month) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseMonth > month) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseMonth == month) {
                                                        if (EqualVoteDatabaseDay < day) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseDay > day) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        } else if (EqualVoteDatabaseDay == day) {
                                                            if (EqualVoteDatabaseHour < hour) {
                                                                tv_currentStage.setText("?????????????? ????????????????");
                                                            } else if (EqualVoteDatabaseHour > hour) {
                                                                tv_currentStage.setText("???????? ??????????????");
                                                            } else if (EqualVoteDatabaseHour == hour) {
                                                                if (EqualVoteDatabaseMinute < minute) {
                                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                                } else if (EqualVoteDatabaseMinute > minute) {
                                                                    tv_currentStage.setText("???????? ??????????????");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (VoteDatabaseHour > hour) {
                                                tv_currentStage.setText("?????????? ??????????????");
                                            } else if (VoteDatabaseHour == hour) {
                                                if (VoteDatabaseMinute < minute) {
//                                                        ?????????????? ??????
                                                    if (EqualVoteDatabaseYear < year) {
                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                    } else if (EqualVoteDatabaseYear > year) {
                                                        tv_currentStage.setText("???????? ??????????????");
                                                    } else if (EqualVoteDatabaseYear == year) {
                                                        if (EqualVoteDatabaseMonth < month) {
                                                            tv_currentStage.setText("?????????????? ????????????????");
                                                        } else if (EqualVoteDatabaseMonth > month) {
                                                            tv_currentStage.setText("???????? ??????????????");
                                                        } else if (EqualVoteDatabaseMonth == month) {
                                                            if (EqualVoteDatabaseDay < day) {
                                                                tv_currentStage.setText("?????????????? ????????????????");
                                                            } else if (EqualVoteDatabaseDay > day) {
                                                                tv_currentStage.setText("???????? ??????????????");
                                                            } else if (EqualVoteDatabaseDay == day) {
                                                                if (EqualVoteDatabaseHour < hour) {
                                                                    tv_currentStage.setText("?????????????? ????????????????");
                                                                } else if (EqualVoteDatabaseHour > hour) {
                                                                    tv_currentStage.setText("???????? ??????????????");
                                                                } else if (EqualVoteDatabaseHour == hour) {
                                                                    if (EqualVoteDatabaseMinute < minute) {
                                                                        tv_currentStage.setText("?????????????? ????????????????");
                                                                    } else if (EqualVoteDatabaseMinute > minute) {
                                                                        tv_currentStage.setText("???????? ??????????????");
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (VoteDatabaseMinute > minute) {
                                                    tv_currentStage.setText("?????????? ??????????????");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (databaseMinute > minute) {
                                tv_currentStage.setText("?????????? ????????????");
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    private ValueEventListener transformListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Calendar myCalendar = Calendar.getInstance();
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);
            int month = myCalendar.get(Calendar.MONTH);
            int year = myCalendar.get(Calendar.YEAR);
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);

            int databaseYear = Integer.parseInt(snapshot.child("vote").child("date").child("year").getValue().toString());
            int databaseMonth = Integer.parseInt(snapshot.child("vote").child("date").child("month").getValue().toString());
            int databaseDay = Integer.parseInt(snapshot.child("vote").child("date").child("day").getValue().toString());
            int databaseHour = Integer.parseInt(snapshot.child("vote").child("time").child("hour").getValue().toString());
            int databaseMinute = Integer.parseInt(snapshot.child("vote").child("time").child("minute").getValue().toString());

            if (databaseYear < year) {
                getWinRaeesAt7ad();
                getWinNaeebRaeesAt7ad();
            } else if (databaseYear > year) {
                afterEndVote();
            } else if (databaseYear == year) {
                if (databaseMonth < month) {
                    getWinRaeesAt7ad();
                    getWinNaeebRaeesAt7ad();
                } else if (databaseMonth > month) {
                    afterEndVote();
                } else if (databaseMonth == month) {
                    if (databaseDay < day) {
                        getWinRaeesAt7ad();
                        getWinNaeebRaeesAt7ad();
                    } else if (databaseDay > day) {
                        afterEndVote();
                    } else if (databaseDay == day) {
                        if (databaseHour < hour) {
                            getWinRaeesAt7ad();
                            getWinNaeebRaeesAt7ad();
                        } else if (databaseHour > hour) {
                            afterEndVote();
                        } else if (databaseHour == hour) {
                            if (databaseMinute < minute) {
                                getWinRaeesAt7ad();
                                getWinNaeebRaeesAt7ad();
                            } else if (databaseMinute > minute) {
                                afterEndVote();
                            }
                        }
                    }
                }
            }

            int EdatabaseYear = Integer.parseInt(snapshot.child("??????????????").child("date").child("year").getValue().toString());
            int EdatabaseMonth = Integer.parseInt(snapshot.child("??????????????").child("date").child("month").getValue().toString());
            int EdatabaseDay = Integer.parseInt(snapshot.child("??????????????").child("date").child("day").getValue().toString());
            int EdatabaseHour = Integer.parseInt(snapshot.child("??????????????").child("time").child("hour").getValue().toString());
            int EdatabaseMinute = Integer.parseInt(snapshot.child("??????????????").child("time").child("minute").getValue().toString());

            if (EdatabaseYear < year) {
                ReVote2getWinRaeesAt7ad();
                ReVote2getWinNaeebRaeesAt7ad();
            } else if (EdatabaseYear > year) {
                currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????????? ?????????? ??????????????.. ");
            } else if (EdatabaseYear == year) {
                if (EdatabaseMonth < month) {
                    ReVote2getWinRaeesAt7ad();
                    ReVote2getWinNaeebRaeesAt7ad();
                } else if (EdatabaseMonth > month) {
                    currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????????? ?????????? ??????????????.. ");
                } else if (EdatabaseMonth == month) {
                    if (EdatabaseDay < day) {
                        ReVote2getWinRaeesAt7ad();
                        ReVote2getWinNaeebRaeesAt7ad();
                    } else if (EdatabaseDay > day) {
                        currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????????? ?????????? ??????????????.. ");
                    } else if (EdatabaseDay == day) {
                        if (EdatabaseHour < hour) {
                            ReVote2getWinRaeesAt7ad();
                            ReVote2getWinNaeebRaeesAt7ad();
                        } else if (EdatabaseHour > hour) {
                            currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????????? ?????????? ??????????????.. ");
                        } else if (EdatabaseHour == hour) {
                            if (EdatabaseMinute < minute) {
                                ReVote2getWinRaeesAt7ad();
                                ReVote2getWinNaeebRaeesAt7ad();
                            } else if (EdatabaseMinute > minute) {
                                currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????????? ?????????? ??????????????.. ");
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__admin);
        drawerLayout = findViewById(R.id.drawer_layout2);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("Users");
        currentStage = database.getReference("CurrentStage");
        currentMembers = database.getReference("?????????????? ????????????????");
        dbEqualsVotes = database.getReference("?????????????? ????????????????????");

        txt_winner1 = findViewById(R.id.txt_winner1);
        txt_winner2 = findViewById(R.id.txt_winner2);
        tv_currentStage = findViewById(R.id.tv_currentStageAdmin);

        currentStage.addValueEventListener(currentStageListener);

        transFromWinnerToCurrent();
        showWinnersFromCurrent();

        recyclerView = findViewById(R.id.rv_admin);
        db = FirebaseDatabase.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Home_Admin.this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(Home_Admin.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        getList();

    }

    private void getList() {
        db.getReference("NewsMessages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list = new ArrayList<>();
                notificationAdapter = new NewsMessages(Home_Admin.this, list);
                recyclerView.setAdapter(notificationAdapter);
                for (DataSnapshot child : snapshot.getChildren()) {
                    MessageModel contractorModel = child.getValue(MessageModel.class);
                    list.add(contractorModel);
                    notificationAdapter.notifyItemInserted(list.size() - 1);
                    notificationAdapter.getItemCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_Admin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void transFromWinnerToCurrent() { currentStage.addValueEventListener(transformListener); }

    // ?????????? ?????????????? ?????????? ???????????? ???????? ???????? ??????????
    private void getWinRaeesAt7ad() {

        reference = FirebaseDatabase.getInstance()
                .getReference().child("????????????????").child("???????? ?????????? ????????????");
        Query query = reference.orderByChild("count").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        countwin = String.valueOf(snapshot.child("count").getValue());
                        sameNumberOfVoteRaeesAt7ad(countwin);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // ???? ?????? ???????? ???????????? ?????????? ???? ???????? ?????????????? ???? ???????????? ?????????? ?????? ?????? ???????????????? ?????????? ????????
    private void sameNumberOfVoteRaeesAt7ad(String countwin) {
        final List<Winers> winnerList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance()
                .getReference().child("????????????????").child("???????? ?????????? ????????????");
        Query query = reference.orderByChild("count").equalTo(Integer.parseInt(countwin));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String namewin = String.valueOf(snapshot1.child("name").getValue());
                        Integer countwin = Integer.parseInt(snapshot1.child("count").getValue().toString());
                        String idtwin = String.valueOf(snapshot1.child("id").getValue());
                        winnerList.add(new Winers(namewin, countwin, idtwin));
                    }
                }
                if (winnerList.size() > 1) {
                    //handle here to start process again
                    for (Winers item : winnerList) {
                        item.getName();
                        item.getCount();
                        item.getId();
                        String SSN = item.getId();
                        dbEqualsVotes.child("???????? ?????????? ????????????").child(SSN).setValue(item);
                        reference = FirebaseDatabase.getInstance()
                                .getReference().child("????????????????").child("???????? ?????????? ????????????").child("name");
                        reference.setValue("???? ???????????? ??????????????..");
                        currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????????? ??????????????..");

                    }

                } else {
                    //only one winner
                    for (Winers item : winnerList) {
                        item.getName();
                        item.getCount();
                        item.getId();
                        reference = FirebaseDatabase.getInstance()
                                .getReference().child("????????????????").child("???????? ?????????? ????????????");
                        reference.setValue(item);
                        currentMembers.child("???????? ?????????? ????????????").setValue(item);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getWinNaeebRaeesAt7ad() {
        reference = FirebaseDatabase.getInstance()
                .getReference().child("????????????????").child("???????? ???????? ?????????? ????????????");
        Query query = reference.orderByChild("count").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        countwin = String.valueOf(snapshot.child("count").getValue());
                        sameNumberOfVoteNaeebRaeesAt7ad(countwin);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sameNumberOfVoteNaeebRaeesAt7ad(String countwin) {
        final List<Winers> winnerList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance()
                .getReference().child("????????????????").child("???????? ???????? ?????????? ????????????");
        Query query = reference.orderByChild("count").equalTo(Integer.parseInt(countwin));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String namewin = String.valueOf(snapshot1.child("name").getValue());
                        Integer countwin = Integer.parseInt(snapshot1.child("count").getValue().toString());
                        String idtwin = String.valueOf(snapshot1.child("id").getValue());
                        winnerList.add(new Winers(namewin, countwin, idtwin));
                    }
                }
                if (winnerList.size() > 1) {
                    //handle here to start process again
                    for (Winers item : winnerList) {
                        item.getName();
                        item.getCount();
                        item.getId();
                        String SSN = item.getId();
                        dbEqualsVotes.child("???????? ???????? ?????????? ????????????").child(SSN).setValue(item);
                        reference = FirebaseDatabase.getInstance()
                                .getReference().child("????????????????").child("???????? ???????? ?????????? ????????????").child("name");
                        reference.setValue("???? ???????? ??????????");
                        currentMembers.child("???????? ???????? ?????????? ????????????").child("name").setValue("???? ???????? ??????????");


                    }

                } else {
                    //only one winner
                    for (Winers item : winnerList) {
                        item.getName();
                        item.getCount();
                        item.getId();
                        String SSN = item.getId();
                        reference = FirebaseDatabase.getInstance()
                                .getReference().child("????????????????").child("???????? ???????? ?????????? ????????????");
                        reference.setValue(item);
                        currentMembers.child("???????? ???????? ?????????? ????????????").setValue(item);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showWinnersFromCurrent() {
        currentMembers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("???????? ?????????? ????????????").child("name").getValue().toString();
                txt_winner1.setText(name);
                String name2 = snapshot.child("???????? ???????? ?????????? ????????????").child("name").getValue().toString();
                txt_winner2.setText(name2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // ??????????????
    private void ReVote2getWinRaeesAt7ad() {
        reference = FirebaseDatabase.getInstance()
                .getReference().child("?????????????? ????????????????????").child("???????? ?????????? ????????????");
        Query query = reference.orderByChild("count").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        countwin = String.valueOf(snapshot.child("count").getValue());

                        final List<Winers> winnerList = new ArrayList<>();
                        reference = FirebaseDatabase.getInstance()
                                .getReference().child("?????????????? ????????????????????").child("???????? ?????????? ????????????");
                        Query query = reference.orderByChild("count").equalTo(Integer.parseInt(countwin));
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        String namewin = String.valueOf(snapshot1.child("name").getValue());
                                        Integer countwin = Integer.parseInt(snapshot1.child("count").getValue().toString());
                                        String idtwin = String.valueOf(snapshot1.child("id").getValue());
                                        winnerList.add(new Winers(namewin, countwin, idtwin));
                                    }
                                }
                                if (winnerList.size() > 1) {
                                    currentMembers.child("???????? ?????????? ????????????").child("name").setValue("???? ???????? ??????????");

                                } else {
                                    //only one winner
                                    for (Winers item : winnerList) {
                                        item.getName();
                                        item.getCount();
                                        item.getId();
                                        String SSN = item.getId();
                                        reference = FirebaseDatabase.getInstance()
                                                .getReference().child("????????????????").child("???????? ?????????? ????????????");
                                        reference.setValue(item);
                                        currentMembers.child("???????? ?????????? ????????????").setValue(item);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ReVote2getWinNaeebRaeesAt7ad() {
        reference = FirebaseDatabase.getInstance()
                .getReference().child("?????????????? ????????????????????").child("???????? ???????? ?????????? ????????????");
        Query query = reference.orderByChild("count").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    if (datasnapshot.exists()) {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            String countwin = String.valueOf(snapshot.child("count").getValue().toString());

                            final List<Winers> winnerList = new ArrayList<>();
                            reference = FirebaseDatabase.getInstance()
                                    .getReference().child("?????????????? ????????????????????").child("???????? ???????? ?????????? ????????????");
                            Query query = reference.orderByChild("count").equalTo(Integer.parseInt(countwin));
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                            String namewin = String.valueOf(snapshot1.child("name").getValue());
                                            Integer countwin = Integer.parseInt(snapshot1.child("count").getValue().toString());
                                            String idtwin = String.valueOf(snapshot1.child("id").getValue());
                                            winnerList.add(new Winers(namewin, countwin, idtwin));
                                        }
                                    }
                                    if (winnerList.size() > 1) {
                                        currentMembers.child("???????? ???????? ?????????? ????????????").child("name").setValue("???? ???????? ??????????");
                                    } else {
                                        //only one winner
                                        for (Winers item : winnerList) {
                                            item.getName();
                                            item.getCount();
                                            item.getId();
                                            String SSN = item.getId();
                                            reference = FirebaseDatabase.getInstance()
                                                    .getReference().child("????????????????").child("???????? ???????? ?????????? ????????????");
                                            currentMembers.child("???????? ???????? ?????????? ????????????").setValue(item);
                                            reference.setValue(item);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void openDialog() {
        DialogTimeAndDate dialogForDate = new DialogTimeAndDate();
        dialogForDate.show(getSupportFragmentManager(), "choose date");
    }

    private void openDialog3() {
        DialogTimeAndDate222 dialogForDate = new DialogTimeAndDate222();
        dialogForDate.show(getSupportFragmentManager(), "choose date");
    }

    private void openDialog2() {
        DialogTimeAndDate333 dialogForDate3 = new DialogTimeAndDate333();
        dialogForDate3.show(getSupportFragmentManager(), "choose date");
    }

    @Override
    public void fromDialogToActiveNomination(int year, int month, int day, int hour, int minute) {
        dataNomination = year + "/" + month + "/" + day;
        notDateNomination = year + "/" + month + "/" + day;
        currentStage.child("nomination").child("date").child("year").setValue(year);
        currentStage.child("nomination").child("date").child("month").setValue(month);
        currentStage.child("nomination").child("date").child("day").setValue(day);
        currentStage.child("nomination").child("time").child("hour").setValue(hour);
        currentStage.child("nomination").child("time").child("minute").setValue(minute);
        Long date=new Date().getTime();

        Toast.makeText(this, "???? ?????????? ????????????", Toast.LENGTH_LONG).show();
        gettokNomitaionNotification();
    }

    @Override
    public void fromDialogToActiveVote(int year, int month, int day, int hour, int minute) {
        dateVote = year + "/" + month + "/" + day;
        currentStage.child("vote").child("date").child("year").setValue(year);
        currentStage.child("vote").child("date").child("month").setValue(month);
        currentStage.child("vote").child("date").child("day").setValue(day);
        currentStage.child("vote").child("time").child("hour").setValue(hour);
        currentStage.child("vote").child("time").child("minute").setValue(minute);

        Toast.makeText(this, "???? ?????????? ??????????????", Toast.LENGTH_SHORT).show();
        gettokVotesNotification();
    }

    @Override
    public void fromDialogToActiveEqaulsVotes(int year, int month, int day, int hour, int minute) {
        dateEqualVote = year + "/" + month + "/" + day;
        ;
        currentStage.child("??????????????").child("date").child("year").setValue(year);
        currentStage.child("??????????????").child("date").child("month").setValue(month);
        currentStage.child("??????????????").child("date").child("day").setValue(day);
        currentStage.child("??????????????").child("time").child("hour").setValue(hour);
        currentStage.child("??????????????").child("time").child("minute").setValue(minute);


        Toast.makeText(this, "???? ?????????? ??????????????", Toast.LENGTH_SHORT).show();
        gettokEqaulsVotesNotification();
    }

    @Override
    protected void onStop() {
        closeDrawer(drawerLayout);
        super.onStop();
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout dr) {
        if (dr.isDrawerOpen((GravityCompat.START))) {
            dr.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public void activeNomination(View view) { openDialog(); }

    public void activeVote(View view) { openDialog3(); }

    public void activeReVote2(View view) { openDialog2(); }

    public void showCandidate(View view) {
        Intent intent3 = new Intent(Home_Admin.this, ShowNominationForAdmin.class);
        startActivity(intent3);
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, SignIn.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent2);
        finish();
    }

    public void AdminClickLogOut(View view) {
        currentStage.removeEventListener(currentStageListener);
        currentStage.removeEventListener(transformListener);
        Intent intent2 = new Intent(Home_Admin.this, SignIn.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent2);
        finish();
    }

    public void gettokNomitaionNotification() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference employeesRef = rootRef.child("Users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("token").exists()
                            && ds.child("nationality").getValue().toString().equals("1")
                            && ds.child("reputation").getValue().toString().equals("1")
                            && ds.child("year").getValue().toString() != "1") {
                        String tokken = (String) ds.child("token").getValue();

                        String title = "???? ?????????? ?????????? ????????????";
                        String message = "?????????? ???????????? ?????????? ???????????? : " + dataNomination;

                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            String token = tokken;
                                        }
                                    }
                                });
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                tokken, title,
                                message, getApplicationContext(), Home_Admin.this);
                        notificationsSender.SendNotifications();
                    } else {
                        //Do something else
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        employeesRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public void gettokVotesNotification() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference employeesRef = rootRef.child("Users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("token").exists()
                            && ds.child("nationality").getValue().toString().equals("1")
                            && ds.child("reputation").getValue().toString().equals("1")
                            && ds.child("year").getValue().toString() != "1") {
                        String tokken = (String) ds.child("token").getValue();

                        String title = "???? ?????????? ?????????? ??????????????";
                        String message = "?????????? ???????????? ?????????? ?????????????? : " + dateVote;

                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            String token = tokken;
                                        }
                                    }
                                });
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                tokken, title,
                                message, getApplicationContext(), Home_Admin.this);
                        notificationsSender.SendNotifications();
                    } else {
                        //Do something else
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        employeesRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public void gettokEqaulsVotesNotification() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference employeesRef = rootRef.child("Users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("token").exists()
                            && ds.child("nationality").getValue().toString().equals("1")
                            && ds.child("reputation").getValue().toString().equals("1")
                            && ds.child("year").getValue().toString() != "1") {
                        String tokken = (String) ds.child("token").getValue();

                        String title = "???? ?????????? ?????????? ??????????????";
                        String message = "?????????? ???????????? ?????????? ?????????????? : " + dateEqualVote;

                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            String token = tokken;
                                        }
                                    }
                                });
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                tokken, title,
                                message, getApplicationContext(), Home_Admin.this);
                        notificationsSender.SendNotifications();
                    } else {
                        //Do something else
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        employeesRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void afterEndVote() {
        reference = FirebaseDatabase.getInstance()
                .getReference().child("????????????????");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name1 = snapshot.child("???????? ?????????? ????????????").child("name").getValue().toString();
                String name2 = snapshot.child("???????? ???????? ?????????? ????????????").child("name").getValue().toString();
                currentMembers.child("???????? ?????????? ????????????").child("name").setValue(name1);
                currentMembers.child("???????? ???????? ?????????? ????????????").child("name").setValue(name2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
